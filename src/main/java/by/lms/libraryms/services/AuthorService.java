package by.lms.libraryms.services;

import by.lms.libraryms.domain.Author;
import by.lms.libraryms.services.searchreq.AuthorReq;

import java.util.List;

public interface AuthorService {
    String addAuthor(Author author);
    String deleteAuthor(String id);
    String updateAuthor(Author author);
    Author getAuthor(AuthorReq searchReq);
    List<Author> getAllAuthors();
    List<Author> getAuthors(AuthorReq searchReq);
}
