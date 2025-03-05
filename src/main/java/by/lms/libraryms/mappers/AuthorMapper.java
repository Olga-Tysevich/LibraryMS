package by.lms.libraryms.mappers;

import by.lms.libraryms.domain.Author;
import by.lms.libraryms.dto.req.AuthorDTO;
import by.lms.libraryms.dto.req.AuthorSearchReqDTO;
import by.lms.libraryms.services.searchobjects.AuthorSearchReq;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface AuthorMapper extends ObjectMapper<Author, AuthorDTO, AuthorSearchReq, AuthorSearchReqDTO> {
}
