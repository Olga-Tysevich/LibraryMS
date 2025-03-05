package by.lms.libraryms.mappers;

import by.lms.libraryms.domain.Book;
import by.lms.libraryms.dto.req.BookDTO;
import by.lms.libraryms.dto.req.BookSearchReqDTO;
import by.lms.libraryms.services.searchobjects.BookSearchReq;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface BookMapper extends ObjectMapper<Book, BookDTO, BookSearchReq, BookSearchReqDTO> {

    @Override
    @Mappings({
            @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "mapLocalDateTimeToInstant"),
            @Mapping(target = "updatedAt", source = "updatedAt", qualifiedByName = "mapLocalDateTimeToInstant"),
            @Mapping(target = "authorIds", source = "authorIds", qualifiedByName = "mapStringSetToObjectIdSet"),
            @Mapping(target = "genreIds", source = "genreIds", qualifiedByName = "mapStringSetToObjectIdSet")
    })
    Book toEntity(BookDTO dto);

    @Override
    @Mappings({
            @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "mapInstantToLocalDateTime"),
            @Mapping(target = "updatedAt", source = "updatedAt", qualifiedByName = "mapInstantToLocalDateTime"),
            @Mapping(target = "authorIds", source = "authorIds", qualifiedByName = "mapObjectIdSetToStringSet"),
            @Mapping(target = "genreIds", source = "genreIds", qualifiedByName = "mapObjectIdSetToStringSet")
    })
    BookDTO toDTO(Book entity);
}
