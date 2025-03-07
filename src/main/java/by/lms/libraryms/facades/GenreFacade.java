package by.lms.libraryms.facades;

import by.lms.libraryms.domain.Genre;
import by.lms.libraryms.dto.req.GenreDTO;
import by.lms.libraryms.dto.req.GenreSearchReqDTO;
import by.lms.libraryms.mappers.GenreMapper;
import by.lms.libraryms.services.GenreService;
import by.lms.libraryms.services.messages.GenreMessageService;
import by.lms.libraryms.services.searchobjects.GenreSearchReq;

public interface GenreFacade extends AbstractFacade<Genre, GenreDTO,
        GenreSearchReq, GenreSearchReqDTO,
        GenreService, GenreMessageService,
        GenreMapper> {

}
