package by.lms.libraryms.services;

import by.lms.libraryms.domain.Genre;
import by.lms.libraryms.dto.req.GenreDTO;
import by.lms.libraryms.dto.req.GenreSearchReqDTO;
import by.lms.libraryms.mappers.GenreMapper;
import by.lms.libraryms.services.searchobjects.GenreSearchReq;

public interface GenreService extends AbstractService<Genre, GenreDTO,
        GenreSearchReq, GenreSearchReqDTO,
        GenreMapper> {
}
