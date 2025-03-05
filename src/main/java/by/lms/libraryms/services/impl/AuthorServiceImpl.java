package by.lms.libraryms.services.impl;

import by.lms.libraryms.domain.Author;
import by.lms.libraryms.dto.req.AuthorDTO;
import by.lms.libraryms.dto.req.AuthorSearchReqDTO;
import by.lms.libraryms.mappers.AuthorMapper;
import by.lms.libraryms.repo.AuthorRepo;
import by.lms.libraryms.repo.search.AuthorSearch;
import by.lms.libraryms.services.AuthorService;
import by.lms.libraryms.services.searchobjects.AuthorSearchReq;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthorServiceImpl extends AbstractServiceImpl<Author, AuthorDTO,
        AuthorSearchReq, AuthorSearchReqDTO,
        AuthorRepo, AuthorSearch,
        AuthorMapper>
        implements AuthorService {

    @Override
    protected Class<Author> clazz() {
        return Author.class;
    }

}
