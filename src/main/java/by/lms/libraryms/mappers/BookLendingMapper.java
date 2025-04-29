package by.lms.libraryms.mappers;

import by.lms.libraryms.domain.BookLending;
import by.lms.libraryms.dto.req.BookLendingDTO;
import by.lms.libraryms.dto.req.BookLendingSearchReqDTO;
import by.lms.libraryms.services.searchobjects.BookLendingSearchReq;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface BookLendingMapper extends ObjectMapper<BookLending, BookLendingDTO, BookLendingSearchReq, BookLendingSearchReqDTO> {

    @Override
    @Mappings({
            @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "mapLocalDateTimeToInstant"),
            @Mapping(target = "updatedAt", source = "updatedAt", qualifiedByName = "mapLocalDateTimeToInstant"),
            @Mapping(target = "inventoryBookId", source = "inventoryBookId", qualifiedByName = "mapStringToObjectId"),
            @Mapping(target = "readerId", source = "readerId", qualifiedByName = "mapStringToObjectId"),
            @Mapping(target = "librarianId", source = "librarianId", qualifiedByName = "mapStringToObjectId")
    })
    BookLending toEntity(BookLendingDTO dto);

    @Override
    @Mappings({
            @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "mapLocalDateTimeToInstant"),
            @Mapping(target = "updatedAt", source = "updatedAt", qualifiedByName = "mapLocalDateTimeToInstant"),
            @Mapping(target = "inventoryBookId", source = "inventoryBookId", qualifiedByName = "mapObjectIdToString"),
            @Mapping(target = "readerId", source = "readerId", qualifiedByName = "mapObjectIdToString"),
            @Mapping(target = "librarianId", source = "librarianId", qualifiedByName = "mapObjectIdToString")
    })
    BookLendingDTO toDTO(BookLending bookLending);

    @Override
    @Mappings({
            @Mapping(target = "ids", source = "ids", qualifiedByName = "mapStringSetToObjectIdSet"),
            @Mapping(target = "createdAtFrom", source = "createdAtFrom", qualifiedByName = "mapLocalDateTimeToInstant"),
            @Mapping(target = "createdAtTo", source = "createdAtTo", qualifiedByName = "mapLocalDateTimeToInstant"),
            @Mapping(target = "updatedAtFrom", source = "updatedAtFrom", qualifiedByName = "mapLocalDateTimeToInstant"),
            @Mapping(target = "updatedAtTo", source = "updatedAtTo", qualifiedByName = "mapLocalDateTimeToInstant"),
            @Mapping(target = "direction", source = "direction", qualifiedByName = "mapStringToDirection"),
            @Mapping(target = "orderBy", source = "orderBy", qualifiedByName = "mapStringToOrder"),
            @Mapping(target = "inventoryBookIds", source = "inventoryBookIds", qualifiedByName = "mapStringSetToObjectIdSet"),
            @Mapping(target = "readerIds", source = "readerIds", qualifiedByName = "mapStringSetToObjectIdSet"),
            @Mapping(target = "librarianIds", source = "librarianIds", qualifiedByName = "mapStringSetToObjectIdSet")
    })
    BookLendingSearchReq toSearchReq(BookLendingSearchReqDTO searchReqDTO);
}
