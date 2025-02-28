package by.lms.libraryms.services;

import by.lms.libraryms.domain.Author;
import by.lms.libraryms.services.searchobjects.AuthorReq;
import by.lms.libraryms.services.searchobjects.ListForPageResp;

public interface AuthorService {
    Author addAuthor(Author author);
    Author deleteAuthor(AuthorReq searchReq);
    Author updateAuthor(Author author);
    Author getAuthor(AuthorReq searchReq);
    ListForPageResp<Author> getAuthors(AuthorReq searchReq);
}
