package by.lms.libraryms.services.impl;

import by.lms.libraryms.domain.Book;
import by.lms.libraryms.dto.req.BookDTO;
import by.lms.libraryms.dto.req.BookSearchReqDTO;
import by.lms.libraryms.mappers.BookMapper;
import by.lms.libraryms.repo.BookRepo;
import by.lms.libraryms.repo.search.BookSearch;
import by.lms.libraryms.services.BookService;
import by.lms.libraryms.services.searchobjects.BookSearchReq;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl extends AbstractServiceImpl<Book, BookDTO,
        BookSearchReq, BookSearchReqDTO,
        BookRepo, BookSearch,
        BookMapper>
        implements BookService {

    public BookServiceImpl(BookRepo repository,
                           BookSearch searchRepo,
                           BookMapper mapper) {
        super(repository, searchRepo, mapper);
    }

    @Override
    protected Class<Book> clazz() {
        return Book.class;
    }
}
