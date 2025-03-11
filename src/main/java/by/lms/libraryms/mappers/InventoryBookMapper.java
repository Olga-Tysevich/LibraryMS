package by.lms.libraryms.mappers;

import by.lms.libraryms.domain.InventoryBook;
import by.lms.libraryms.domain.InventoryNumber;
import by.lms.libraryms.dto.req.BookDTO;
import by.lms.libraryms.dto.req.InventoryBookDTO;
import by.lms.libraryms.dto.req.InventoryBookSearchReqDTO;
import by.lms.libraryms.dto.resp.ObjectChangedDTO;
import by.lms.libraryms.exceptions.ObjectNotFound;
import by.lms.libraryms.repo.BookRepo;
import by.lms.libraryms.repo.InventoryNumberRepo;
import by.lms.libraryms.services.searchobjects.BookSearchReq;
import by.lms.libraryms.services.searchobjects.InventoryBookSearchReq;
import org.bson.types.ObjectId;
import org.mapstruct.*;
import org.springframework.lang.NonNull;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface InventoryBookMapper extends ObjectMapper<InventoryBook, InventoryBookDTO, InventoryBookSearchReq, InventoryBookSearchReqDTO> {
    @Override
    @Mappings({
            @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "mapLocalDateTimeToInstant"),
            @Mapping(target = "updatedAt", source = "updatedAt", qualifiedByName = "mapLocalDateTimeToInstant"),
            @Mapping(target = "bookId", source = "book.id", qualifiedByName = "mapStringToObjectId"),
            @Mapping(target = "inventoryNumberId", ignore = true),
            @Mapping(target = "bookOrderIds", source = "bookOrderIds", qualifiedByName = "mapStringSetToObjectIdSet"),
            @Mapping(target = "version", ignore = true)
    })
    InventoryBook toEntity(InventoryBookDTO dto);

    @Override
    @Mappings({
            @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "mapInstantToLocalDateTime"),
            @Mapping(target = "updatedAt", source = "updatedAt", qualifiedByName = "mapInstantToLocalDateTime"),
            @Mapping(target = "book", ignore = true),
            @Mapping(target = "inventoryNumber", ignore = true),
            @Mapping(target = "bookOrderIds", source = "bookOrderIds", qualifiedByName = "mapObjectIdSetToStringSet"),
            @Mapping(target = "isAvailable", source = "available")
    })
    InventoryBookDTO toDTO(InventoryBook entity);

    @Override
    @Mappings({
            @Mapping(target = "createdAtFrom", source = "createdAtFrom", qualifiedByName = "mapLocalDateTimeToInstant"),
            @Mapping(target = "createdAtTo", source = "createdAtTo", qualifiedByName = "mapLocalDateTimeToInstant"),
            @Mapping(target = "updatedAtFrom", source = "updatedAtFrom", qualifiedByName = "mapLocalDateTimeToInstant"),
            @Mapping(target = "updatedAtTo", source = "updatedAtTo", qualifiedByName = "mapLocalDateTimeToInstant"),
            @Mapping(target = "direction", source = "direction", qualifiedByName = "mapStringToDirection"),
            @Mapping(target = "orderBy", source = "orderBy", qualifiedByName = "mapStringToOrder"),
            @Mapping(target = "inventoryNumbers", ignore = true),
            @Mapping(target = "authorIds", source = "authorIds", qualifiedByName = "mapStringSetToObjectIdSet"),
            @Mapping(target = "genreIds", source = "genreIds", qualifiedByName = "mapStringSetToObjectIdSet"),
            @Mapping(target = "bookOrderIds", source = "bookOrderIds", qualifiedByName = "mapStringSetToObjectIdSet")
    })
    InventoryBookSearchReq toSearchReq(InventoryBookSearchReqDTO searchReqDTO);

    BookSearchReq toBookSearchReq(InventoryBookSearchReq searchReq);

    @Mappings({
            @Mapping(target = "object", source = "object", qualifiedByName = "mapInventoryToBook")
    })
    ObjectChangedDTO<BookDTO> toBookChangedDTO(ObjectChangedDTO<InventoryBookDTO> dto);

    @Named("mapInventoryToBook")
    @Mappings({
            @Mapping(target = "title", source = "book.title"),
            @Mapping(target = "year", source = "book.year"),
            @Mapping(target = "authorIds", source = "book.authorIds"),
            @Mapping(target = "genreIds", source = "book.genreIds"),
            @Mapping(target = "uniqueKey", source = "book.uniqueKey"),
    })
    BookDTO toBookDTO(InventoryBookDTO inventoryBookDTO);

    @AfterMapping
    default void mapInventoryNumber(@MappingTarget InventoryBook entity, InventoryBookDTO dto,
                                    @Context InventoryNumberMapper inventoryNumberMapper,
                                    @Context InventoryNumberRepo numberRepo) {
        if (dto.getInventoryNumber() != null) {
            InventoryNumber example = inventoryNumberMapper.toInventoryNumber(dto.getInventoryNumber());
            InventoryNumber result = numberRepo.find(example).orElseThrow(ObjectNotFound::new);
            entity.setInventoryNumberId(new ObjectId(result.getId()));
        }
    }

    @AfterMapping
    default void mapBookIdToBookDTO(@MappingTarget InventoryBookDTO dto, @NonNull ObjectId id,
                                    @Context BookRepo bookRepo, @Context BookMapper bookMapper) {
        BookDTO book = bookRepo.findById(id.toString())
                .map(bookMapper::toDTO)
                .orElseThrow(ObjectNotFound::new);
        dto.setBook(book);
    }

    @AfterMapping
    default void mapInventoryNumberIdToInventoryNumber(@MappingTarget InventoryBookDTO dto, @NonNull ObjectId id,
                                                       @Context InventoryNumberRepo numberRepo) {
        String inventoryNumber = numberRepo.findById(id)
                .map(InventoryNumber::number)
                .orElseThrow(ObjectNotFound::new);
        dto.setInventoryNumber(inventoryNumber);
    }
}
