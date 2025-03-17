package by.lms.libraryms.mappers;

import by.lms.libraryms.domain.inventorynumber.InventoryNumber;
import by.lms.libraryms.domain.StockBook;
import by.lms.libraryms.dto.req.*;
import by.lms.libraryms.dto.resp.ObjectChangedDTO;
import by.lms.libraryms.repo.InventoryNumberRepo;
import by.lms.libraryms.services.searchobjects.InventoryBookSearchReq;
import by.lms.libraryms.services.searchobjects.StockBookSearchReq;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.bson.types.ObjectId;
import org.mapstruct.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface StockBookMapper extends ObjectMapper<StockBook, StockBookDTO,
        StockBookSearchReq, StockBookSearchReqDTO> {

    @Override
    @Mappings({
            @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "mapLocalDateTimeToInstant"),
            @Mapping(target = "updatedAt", source = "updatedAt", qualifiedByName = "mapLocalDateTimeToInstant"),
            @Mapping(target = "bookId", source = "bookId", qualifiedByName = "mapStringToObjectId"),
            @Mapping(target = "dateOfReceipt", source = "dateOfReceipt", qualifiedByName = "mapLocalDateToInstant")
    })
    StockBook toEntity(StockBookDTO dto);

    @Override
    @Mappings({
            @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "mapInstantToLocalDateTime"),
            @Mapping(target = "updatedAt", source = "updatedAt", qualifiedByName = "mapInstantToLocalDateTime"),
            @Mapping(target = "bookId", source = "bookId", qualifiedByName = "mapObjectIdToString"),
            @Mapping(target = "dateOfReceipt", source = "dateOfReceipt", qualifiedByName = "mapInstantToLocalDate")
    })
    StockBookDTO toDTO(StockBook entity);

    @Override
    @Mappings({
            @Mapping(target = "createdAtFrom", source = "createdAtFrom", qualifiedByName = "mapLocalDateTimeToInstant"),
            @Mapping(target = "createdAtTo", source = "createdAtTo", qualifiedByName = "mapLocalDateTimeToInstant"),
            @Mapping(target = "updatedAtFrom", source = "updatedAtFrom", qualifiedByName = "mapLocalDateTimeToInstant"),
            @Mapping(target = "updatedAtTo", source = "updatedAtTo", qualifiedByName = "mapLocalDateTimeToInstant"),
            @Mapping(target = "direction", source = "direction", qualifiedByName = "mapStringToDirection"),
            @Mapping(target = "orderBy", source = "orderBy", qualifiedByName = "mapStringToOrder"),
            @Mapping(target = "authorIds", source = "authorIds", qualifiedByName = "mapStringSetToObjectIdSet"),
            @Mapping(target = "genreIds", source = "genreIds", qualifiedByName = "mapStringSetToObjectIdSet"),
            @Mapping(target = "bookIds", source = "bookIds", qualifiedByName = "mapStringSetToObjectIdSet"),
            @Mapping(target = "numberOfBooks", source = "numberOfBooks", qualifiedByName = "mapStringIntegerMapToObjectIdIntegerMap"),
            @Mapping(target = "inventoryNumbers", ignore = true),
            @Mapping(target = "dateOfReceiptFrom", source = "dateOfReceiptFrom", qualifiedByName = "mapLocalDateToInstant"),
            @Mapping(target = "dateOfReceiptTo", source = "dateOfReceiptTo", qualifiedByName = "mapLocalDateToInstant")
    })
    StockBookSearchReq toSearchReq(StockBookSearchReqDTO searchReqDTO);

    @Mappings({
            @Mapping(target = "objects", source = "bookDTOList")
    })
    ObjectChangedDTO<BookDTO> toBookChangedDTO(ObjectChangedDTO<StockBookDTO> dto, List<BookDTO> bookDTOList);

    @Mappings({
            @Mapping(target = "inventoryNumbers", ignore = true)
    })
    InventoryBookSearchReq toBookSearchReq(StockBookSearchReq searchReq);

    @Mappings({
            @Mapping(target = "id", source = "id", qualifiedByName = "mapObjectIdToString"),
            @Mapping(target = "book", source = "bookDTO"),
            @Mapping(target = "dateOfReceipt", source = "dateOfReceipt")
    })
    InventoryBookDTO toInventoryBookDTO(ObjectId id, BookDTO bookDTO, LocalDate dateOfReceipt);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "objectClass", expression = "java(books.get(0).getClass().getSimpleName())"),
            @Mapping(target = "createdAt", expression = "java(books.get(0).getCreatedAt())"),
            @Mapping(target = "updatedAt", expression = "java(books.get(0).getUpdatedAt())"),
            @Mapping(target = "deletedAt", source = "deletedAt", qualifiedByName = "mapInstantToLocalDateTime"),
            @Mapping(target = "object", ignore = true),
            @Mapping(target = "objects", source = "books"),
    })
    ObjectChangedDTO<StockBookDTO> toStockBookChangedDTO(@NotEmpty List<StockBookDTO> books, Instant deletedAt);

    @AfterMapping
    default void mapInventoryNumbers(@MappingTarget StockBookSearchReq target, @NotNull StockBookSearchReqDTO source,
                                     @Context InventoryNumberMapper inventoryNumberMapper,
                                     @Context InventoryNumberRepo numberRepo) {
        if (source.getInventoryNumbers() == null || source.getInventoryNumbers().isEmpty()) {
            target.setInventoryNumbers(Collections.emptySet());
            return;
        }
        Set<ObjectId> result = source.getInventoryNumbers().stream()
                .map(inventoryNumberMapper::toInventoryNumber)
                .filter(Objects::nonNull)
                .map(in -> numberRepo.find(in).orElse(null))
                .filter(Objects::nonNull)
                .map(InventoryNumber::getId)
                .map(ObjectId::new)
                .collect(Collectors.toSet());

        target.setInventoryNumbers(result);
    }

    @Named("mapStringIntegerMapToObjectIdIntegerMap")
    default Map<ObjectId, Integer> mapStringIntegerMapToObjectIdIntegerMap(Map<String, Integer> map) {
        if (Objects.isNull(map)) {
            return null;
        }
        return map.entrySet().stream()
                .collect(Collectors.toMap(entry -> new ObjectId(entry.getKey()), Map.Entry::getValue));
    }

}

