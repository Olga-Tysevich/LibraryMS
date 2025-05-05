package by.lms.libraryms.facades.impl;

import by.lms.libraryms.domain.BookLending;
import by.lms.libraryms.dto.req.BookLendingDTO;
import by.lms.libraryms.dto.req.BookLendingSearchReqDTO;
import by.lms.libraryms.facades.BookLendingFacade;
import by.lms.libraryms.mappers.BookLendingMapper;
import by.lms.libraryms.services.BookLendingService;
import by.lms.libraryms.services.NotificationService;
import by.lms.libraryms.services.messages.BookLendingMessageService;
import by.lms.libraryms.services.searchobjects.BookLendingSearchReq;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class BookLendingFacadeImpl extends AbstractFacadeImpl<BookLending, BookLendingDTO,
        BookLendingSearchReq, BookLendingSearchReqDTO,
        BookLendingService, BookLendingMessageService,
        BookLendingMapper> implements BookLendingFacade {

    public BookLendingFacadeImpl(BookLendingService service,
                                 @Qualifier("telegramNotificationService") NotificationService<BookLendingDTO> notificationService,
                                 BookLendingMessageService messageService) {
        super(service, notificationService, messageService);
    }
}
