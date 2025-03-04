package by.lms.libraryms.mappers;

import by.lms.libraryms.domain.Author;
import by.lms.libraryms.dto.req.AuthorDTO;
import by.lms.libraryms.dto.req.AuthorSearchReqDTO;
import by.lms.libraryms.dto.resp.ListForPageDTO;
import by.lms.libraryms.services.searchobjects.AuthorReq;
import by.lms.libraryms.services.searchobjects.ListForPageResp;
import org.mapstruct.*;
import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface AuthorMapper extends ObjectMapper<Author>, SearchMapper {
    @Mappings({
            @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "mapLocalDateTimeToInstant"),
            @Mapping(target = "updatedAt", source = "updatedAt", qualifiedByName = "mapLocalDateTimeToInstant")
    })
    Author toAuthor(AuthorDTO authorDTO);

    @Mappings({
            @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "mapInstantToLocalDateTime"),
            @Mapping(target = "updatedAt", source = "updatedAt", qualifiedByName = "mapInstantToLocalDateTime")
    })
    AuthorDTO toAuthorDTO(Author author);

    List<AuthorDTO> toAuthorDTOList(List<Author> authors);

    @Mappings({
            @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "mapLocalDateTimeToInstant"),
            @Mapping(target = "updatedAt", source = "updatedAt", qualifiedByName = "mapLocalDateTimeToInstant"),
            @Mapping(target = "direction", source = "direction", qualifiedByName = "mapStringToDirection"),
            @Mapping(target = "orderBy", source = "orderBy", qualifiedByName = "mapStringToOrder")
    })
    AuthorReq toSearchReq(AuthorSearchReqDTO dto);

    ListForPageDTO<AuthorDTO> toListForPageDTO(ListForPageResp<Author> list);

}
