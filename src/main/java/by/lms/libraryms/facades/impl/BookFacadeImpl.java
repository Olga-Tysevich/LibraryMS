package by.lms.libraryms.facades.impl;

import by.lms.libraryms.conf.i18n.MessageTypeEnum;
import by.lms.libraryms.domain.Book;
import by.lms.libraryms.dto.req.BookDTO;
import by.lms.libraryms.dto.req.BookSearchReqDTO;
import by.lms.libraryms.facades.BookFacade;
import by.lms.libraryms.mappers.BookMapper;
import by.lms.libraryms.services.AuthorService;
import by.lms.libraryms.services.BookService;
import by.lms.libraryms.services.searchobjects.BookSearchReq;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class BookFacadeImpl extends AbstractFacadeImpl<Book, BookDTO,
        BookSearchReq, BookSearchReqDTO,
        BookService, BookMapper> implements BookFacade {
    private AuthorService authorService;


//    @Override
//    protected String[] getArgs(BookDTO dto) {
//
//        Object[] result = new Object[5];
//        String authors = "{" + authorService.getAllByIds(dto.getAuthorIds()).stream()
//                .map(a -> a.getName() + a.getSurname())
//                .collect(Collectors.joining(", ")) + "}";
//        //TODO добавить библиотекаря
//        result[0] = "";
//        //TODO обавить жанры
////        List<GenreDTO>
//        return new String[0];
//    }
}
