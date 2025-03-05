package by.lms.libraryms.services;

import by.lms.libraryms.domain.Author;
import by.lms.libraryms.dto.req.AuthorDTO;
import by.lms.libraryms.dto.req.AuthorSearchReqDTO;
import by.lms.libraryms.mappers.AuthorMapper;
import by.lms.libraryms.services.searchobjects.AuthorSearchReq;

public interface AuthorService extends AbstractService<Author, AuthorDTO,
        AuthorSearchReq, AuthorSearchReqDTO,
        AuthorMapper>{
}
