package by.lms.libraryms.services.messages.impl;

import by.lms.libraryms.conf.i18n.MessageTypeEnum;
import by.lms.libraryms.dto.req.BookDTO;
import by.lms.libraryms.dto.resp.ObjectChangedDTO;
import by.lms.libraryms.services.AuthorService;
import by.lms.libraryms.services.messages.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class BookMessageService extends AbstractMessageServiceImpl<BookDTO> implements MessageService<BookDTO> {
    private AuthorService authorService;

    @Override
    public String createMessage(MessageTypeEnum typeEnum, ObjectChangedDTO<BookDTO> bookDTO) {
        String pattern = messageConf().getBookMap().getOrDefault(typeEnum, "");
        Object[] result = new Object[5];
        BookDTO book = bookDTO.getObject();
        if (Objects.nonNull(book)) {
            String authors = "{" + authorService.getAllByIds(book.getAuthorIds()).stream()
                    .map(a -> a.getName() + a.getSurname())
                    .collect(Collectors.joining(", ")) + "}";
            //TODO добавить библиотекаря
            result[0] = "";
            result[1] = authors;
            //TODO обавить жанры
//        List<GenreDTO>
            result[2] = "";
            result[3] = book.getTitle();
            result[4] = book.getYear();
        }

        return super.createMessage(pattern, bookDTO.getUpdatedAt(), result);
    }

}
