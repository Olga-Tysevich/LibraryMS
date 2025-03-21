package by.lms.libraryms.conf.bots;

import by.lms.libraryms.dto.AbstractDTO;
import by.lms.libraryms.dto.req.AuthorDTO;
import by.lms.libraryms.dto.req.BookDTO;
import by.lms.libraryms.dto.req.GenreDTO;
import by.lms.libraryms.dto.req.StockBookDTO;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

//TODO подумать над возможностью создавать собственные для каждой библиотеки
@Component
@Data
public class TelegramGroupMessageConfig {
    @Value("telegram.libraryMsGroup.groupId")
    private String groupId;
    @Getter(AccessLevel.PACKAGE)
    @Value("telegram.libraryMsGroup.topics.notifications_message_thread_id")
    private String notificationsMessageThreadId;
    @Getter(AccessLevel.PACKAGE)
    @Value("telegram.libraryMsGroup.topics.errors_message_thread_id")
    private String errorsMessageThreadId;
    @Getter(AccessLevel.PACKAGE)
    @Value("telegram.libraryMsGroup.topics.book_leading_message_thread_id")
    private String bookLeadingMessageThreadId;
    @Getter(AccessLevel.PACKAGE)
    @Value("telegram.libraryMsGroup.topics.genres_message_thread_id")
    private String genresMessageThreadId;
    @Getter(AccessLevel.PACKAGE)
    @Value("telegram.libraryMsGroup.topics.authors_message_thread_id")
    private String authorsMessageThreadId;
    @Getter(AccessLevel.PACKAGE)
    @Value("telegram.libraryMsGroup.topics.stock_books_message_thread_id")
    private String stockBooksMessageThreadId;
    @Getter(AccessLevel.PACKAGE)
    @Value("telegram.libraryMsGroup.topics.readers_message_thread_id")
    private String readersMessageThreadId;
    @Getter(AccessLevel.PACKAGE)
    @Value("telegram.libraryMsGroup.topics.librarians_message_thread_id")
    private String librariansMessageThreadId;
    @Getter(AccessLevel.PACKAGE)
    @Value("telegram.libraryMsGroup.topics.books_message_thread_id")
    private String booksMessageThreadId;

    private Map<Class<? extends AbstractDTO>, String> messageThreadIdMap = new HashMap<>();

    public TelegramGroupMessageConfig() {
        messageThreadIdMap.put(AuthorDTO.class, authorsMessageThreadId);
        messageThreadIdMap.put(GenreDTO.class, genresMessageThreadId);
        messageThreadIdMap.put(BookDTO.class, booksMessageThreadId);
        messageThreadIdMap.put(StockBookDTO.class, stockBooksMessageThreadId);
    }

    public String getGroupIdForClass(Class<?> clazz) {
        return messageThreadIdMap.getOrDefault(clazz, "");
    }
}
