package by.lms.libraryms.services.impl;

import by.lms.libraryms.domain.Author;
import by.lms.libraryms.repo.AuthorRepo;
import by.lms.libraryms.services.AuthorService;
import by.lms.libraryms.services.searchobjects.AuthorReq;
import by.lms.libraryms.services.searchobjects.ListForPageResp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private AuthorRepo authorRepo;

    @Override
    public Author addAuthor(Author author) {
        return null;
    }

    @Override
    public Author deleteAuthor(AuthorReq searchReq) {
        return null;
    }

    @Override
    public Author updateAuthor(Author author) {
        return null;
    }

    @Override
    public Author getAuthor(AuthorReq searchReq) {
        return null;
    }

    @Override
    public ListForPageResp<Author> getAuthors(AuthorReq searchReq) {
        return null;
    }

}
