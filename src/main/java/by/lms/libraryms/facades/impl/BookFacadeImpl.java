package by.lms.libraryms.facades.impl;

import by.lms.libraryms.conf.i18n.MessageTypeEnum;
import by.lms.libraryms.domain.Book;
import by.lms.libraryms.dto.req.BookDTO;
import by.lms.libraryms.dto.req.BookSearchReqDTO;
import by.lms.libraryms.dto.resp.ObjectChangedDTO;
import by.lms.libraryms.facades.BookFacade;
import by.lms.libraryms.mappers.BookMapper;
import by.lms.libraryms.services.BookService;
import by.lms.libraryms.services.messages.MessageService;
import by.lms.libraryms.services.searchobjects.BookSearchReq;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class BookFacadeImpl extends AbstractFacadeImpl<Book, BookDTO,
        BookSearchReq, BookSearchReqDTO,
        BookService, BookMapper> implements BookFacade {
    private MessageService<BookDTO> messageService;

    @Override
    protected String message(MessageTypeEnum type, ObjectChangedDTO<BookDTO> result) {
        return messageService.createMessage(type, result);
    }

}
