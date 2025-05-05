package by.lms.libraryms.facades;

import by.lms.libraryms.domain.BookLending;
import by.lms.libraryms.dto.req.BookLendingDTO;
import by.lms.libraryms.dto.req.BookLendingSearchReqDTO;
import by.lms.libraryms.mappers.BookLendingMapper;
import by.lms.libraryms.services.BookLendingService;
import by.lms.libraryms.services.messages.BookLendingMessageService;
import by.lms.libraryms.services.searchobjects.BookLendingSearchReq;

public interface BookLendingFacade extends AbstractFacade<BookLending, BookLendingDTO,
        BookLendingSearchReq, BookLendingSearchReqDTO,
        BookLendingService, BookLendingMessageService,
        BookLendingMapper> {

}
