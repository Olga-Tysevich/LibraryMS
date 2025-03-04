package by.lms.libraryms.services.impl;

import by.lms.libraryms.domain.Author;
import by.lms.libraryms.exceptions.ChangingObjectException;
import by.lms.libraryms.exceptions.ObjectNotFound;
import by.lms.libraryms.repo.AuthorRepo;
import by.lms.libraryms.repo.search.AuthorSearch;
import by.lms.libraryms.services.AuthorService;
import by.lms.libraryms.services.searchobjects.AuthorReq;
import by.lms.libraryms.services.searchobjects.ListForPageResp;
import by.lms.libraryms.utils.ParamsManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static by.lms.libraryms.utils.Constants.OBJECTS_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepo authorRepo;
    private final AuthorSearch authorSearch;

    @Override
    public Author addAuthor(Author author) {
        return authorRepo.save(author);
    }

    @Override
    public Author deleteAuthor(AuthorReq searchReq) {
        Author author = findAuthor(searchReq);
        boolean isDeleted = authorSearch.delete(searchReq);
        if (!isDeleted) {
            throw new ChangingObjectException();
        }
        return author;
    }

    @Override
    public Author updateAuthor(Author author) {
        return authorRepo.save(author);
    }

    @Override
    public Author getAuthor(AuthorReq searchReq) {
        return findAuthor(searchReq);
    }

    @Override
    public ListForPageResp<Author> getAuthors(AuthorReq searchReq) {
        return authorSearch.findList(searchReq);
    }

    private Author findAuthor(AuthorReq searchReq) {
        Author author = authorSearch.find(searchReq).getFirst();

        if (Objects.isNull(author)) {
            throw new ObjectNotFound(
                    String.format(OBJECTS_NOT_FOUND,
                            Author.class,
                            ParamsManager.getParamsAsString(searchReq)
                    )
            );
        }

        return author;
    }

}
