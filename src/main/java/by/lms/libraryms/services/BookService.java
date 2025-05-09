package by.lms.libraryms.services;

import by.lms.libraryms.domain.Book;
import by.lms.libraryms.dto.req.BookDTO;
import by.lms.libraryms.dto.req.BookSearchReqDTO;
import by.lms.libraryms.mappers.BookMapper;
import by.lms.libraryms.services.searchobjects.BookSearchReq;

public interface BookService extends AbstractService<Book, BookDTO,
        BookSearchReq, BookSearchReqDTO,
        BookMapper> {
}
