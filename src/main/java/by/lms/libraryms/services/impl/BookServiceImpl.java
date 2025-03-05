package by.lms.libraryms.services.impl;

import by.lms.libraryms.domain.Book;
import by.lms.libraryms.repo.BookRepo;
import by.lms.libraryms.repo.search.BookSearch;
import by.lms.libraryms.services.BookService;
import by.lms.libraryms.services.searchobjects.BookReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl extends AbstractServiceImpl<Book, BookReq, BookRepo, BookSearch>
        implements BookService {

    @Override
    protected Class<Book> clazz() {
        return Book.class;
    }
}
