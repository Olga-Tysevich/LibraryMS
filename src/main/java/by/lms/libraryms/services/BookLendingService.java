package by.lms.libraryms.services;

import by.lms.libraryms.domain.BookLending;
import by.lms.libraryms.dto.req.BookLendingDTO;
import by.lms.libraryms.dto.req.BookLendingSearchReqDTO;
import by.lms.libraryms.mappers.BookLendingMapper;
import by.lms.libraryms.services.searchobjects.BookLendingSearchReq;

public interface BookLendingService extends AbstractService<BookLending, BookLendingDTO,
        BookLendingSearchReq, BookLendingSearchReqDTO,
        BookLendingMapper> {
}
