package by.lms.libraryms.facades.impl;

import by.lms.libraryms.conf.i18n.MessageTypeEnum;
import by.lms.libraryms.domain.Book;
import by.lms.libraryms.dto.req.BookDTO;
import by.lms.libraryms.dto.req.BookSearchReqDTO;
import by.lms.libraryms.dto.resp.ObjectChangedDTO;
import by.lms.libraryms.facades.BookFacade;
import by.lms.libraryms.mappers.BookMapper;
import by.lms.libraryms.services.BookService;
import by.lms.libraryms.services.NotificationService;
import by.lms.libraryms.services.messages.BookMessageService;
import by.lms.libraryms.services.searchobjects.BookSearchReq;
import org.springframework.stereotype.Component;


@Component
public class BookFacadeImpl extends AbstractFacadeImpl<Book, BookDTO,
        BookSearchReq, BookSearchReqDTO,
        BookService, BookMessageService,
        BookMapper> implements BookFacade {

    public BookFacadeImpl(BookService service,
                          NotificationService<BookDTO> notificationService,
                          BookMessageService messageService) {
        super(service, notificationService, messageService);
    }

    @Override
    protected String message(MessageTypeEnum type, ObjectChangedDTO<BookDTO> result) {
        return getMessageService().createMessage(type, result);
    }

}
