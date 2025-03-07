package by.lms.libraryms.services.messages.impl;

import by.lms.libraryms.conf.i18n.MessageConf;
import by.lms.libraryms.conf.i18n.MessageTypeEnum;
import by.lms.libraryms.dto.req.BookDTO;
import by.lms.libraryms.dto.resp.ObjectChangedDTO;
import by.lms.libraryms.services.AuthorService;
import by.lms.libraryms.services.messages.BookMessageService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookMessageServiceImpl extends AbstractMessageServiceImpl<BookDTO> implements BookMessageService {
    private final AuthorService authorService;

    public BookMessageServiceImpl(MessageConf messageConf, AuthorService authorService) {
        super(messageConf);
        this.authorService = authorService;
    }

    @Override
    protected void addSpecific(MessageTypeEnum typeEnum, ObjectChangedDTO<BookDTO> dto, List<Object> args) {
        BookDTO book = dto.getObject();
        String authors = "{" + authorService.getAllByIds(book.getAuthorIds()).stream()
                .map(a -> a.getName() + a.getSurname())
                .collect(Collectors.joining(", ")) + "}";
        args.add(authors);
        //TODO обавить жанры
//        List<GenreDTO>
        args.add("");
        args.add(book.getTitle());
        args.add(book.getYear());
    }

    @Override
    protected String getPattern(MessageTypeEnum typeEnum) {
        return messageConf().getBookMap().getOrDefault(typeEnum, "");
    }

}
