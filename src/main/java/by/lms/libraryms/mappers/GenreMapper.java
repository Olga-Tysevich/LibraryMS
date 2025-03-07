package by.lms.libraryms.mappers;

import by.lms.libraryms.domain.Genre;
import by.lms.libraryms.dto.req.GenreDTO;
import by.lms.libraryms.dto.req.GenreSearchReqDTO;
import by.lms.libraryms.services.searchobjects.GenreSearchReq;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface GenreMapper extends ObjectMapper<Genre, GenreDTO, GenreSearchReq, GenreSearchReqDTO>{
}
