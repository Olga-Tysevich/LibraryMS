package by.lms.libraryms.facades;

import by.lms.libraryms.domain.Book;
import by.lms.libraryms.dto.req.BookDTO;
import by.lms.libraryms.dto.req.BookSearchReqDTO;
import by.lms.libraryms.mappers.BookMapper;
import by.lms.libraryms.services.BookService;
import by.lms.libraryms.services.searchobjects.BookSearchReq;

public interface BookFacade extends AbstractFacade<Book, BookDTO,
        BookSearchReq, BookSearchReqDTO,
        BookService, BookMapper> {
}
