package by.lms.libraryms.facades.impl;

import by.lms.libraryms.conf.i18n.MessageTypeEnum;
import by.lms.libraryms.domain.Book;
import by.lms.libraryms.dto.req.AuthorDTO;
import by.lms.libraryms.dto.req.BookDTO;
import by.lms.libraryms.dto.req.BookSearchReqDTO;
import by.lms.libraryms.facades.BookFacade;
import by.lms.libraryms.mappers.BookMapper;
import by.lms.libraryms.services.AuthorService;
import by.lms.libraryms.services.BookService;
import by.lms.libraryms.services.searchobjects.BookSearchReq;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class BookFacadeImpl extends AbstractFacadeImpl<Book, BookDTO,
        BookSearchReq, BookSearchReqDTO,
        BookService, BookMapper> implements BookFacade {
    private AuthorService authorService;

    @Override
    protected Map<MessageTypeEnum, String> getMessages() {
        return getMessageConf().getBookMap();
    }

    @Override
    protected String[] getArgs(BookDTO dto) {
        List<AuthorDTO> authors = authorService.getAllByIds(dto.getAuthorIds())
                .stream().map();
        return new String[0];
    }
}
