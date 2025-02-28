package by.lms.libraryms.services.impl;

import by.lms.libraryms.domain.Author;
import by.lms.libraryms.repo.AuthorRepo;
import by.lms.libraryms.services.AuthorService;
import by.lms.libraryms.services.searchreq.AuthorReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private AuthorRepo authorRepo;

    @Override
    public String addAuthor(Author author) {
        return "";
    }

    @Override
    public String deleteAuthor(String id) {
        return "";
    }

    @Override
    public String updateAuthor(Author author) {
        return "";
    }

    @Override
    public Author getAuthor(AuthorReq searchReq) {
        return null;
    }

    @Override
    public List<Author> getAllAuthors() {
        return List.of();
    }

    @Override
    public List<Author> getAuthors(AuthorReq searchReq) {
        return List.of();
    }
}
