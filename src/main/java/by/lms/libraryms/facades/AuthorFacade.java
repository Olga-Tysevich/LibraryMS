package by.lms.libraryms.facades;

import by.lms.libraryms.domain.Author;
import by.lms.libraryms.dto.req.AuthorSearchReqDTO;
import by.lms.libraryms.dto.req.AuthorDTO;
import by.lms.libraryms.mappers.AuthorMapper;
import by.lms.libraryms.services.AuthorService;
import by.lms.libraryms.services.searchobjects.AuthorSearchReq;

public interface AuthorFacade extends AbstractFacade<Author, AuthorDTO,
        AuthorSearchReq, AuthorSearchReqDTO,
        AuthorService, AuthorMapper> {
}
