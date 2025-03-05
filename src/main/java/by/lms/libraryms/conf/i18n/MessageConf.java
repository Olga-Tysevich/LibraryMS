package by.lms.libraryms.conf.i18n;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

//TODO разобраться с @PropertySource
@Configuration
@PropertySource("classpath:by/lms/libraryms/i18n/messages.properties")
public class MessageConf {
    //Author
    @Value("${author.created.message}")
    private String authorCreatedMessage;
    @Value("${author.updated.message}")
    private String authorUpdatedMessage;
    @Value("${author.deleted.message}")
    private String authorDeletedMessage;
    //Book
    @Value("${book.created.message}")
    private String bookCreatedMessage;
    @Value("${book.updated.message}")
    private String bookUpdatedMessage;
    @Value("${book.deleted.message}")
    private String bookDeletedMessage;

    @Getter
    private final Map<MessageTypeEnum, String> authorMap = new HashMap<>();
    @Getter
    private final Map<MessageTypeEnum, String> bookMap = new HashMap<>();

    {
        //Author
        authorMap.put(MessageTypeEnum.ADD, authorCreatedMessage);
        authorMap.put(MessageTypeEnum.UPDATE, authorUpdatedMessage);
        authorMap.put(MessageTypeEnum.DELETE, authorDeletedMessage);
        //Book
        bookMap.put(MessageTypeEnum.ADD, bookCreatedMessage);
        bookMap.put(MessageTypeEnum.UPDATE, bookUpdatedMessage);
        bookMap.put(MessageTypeEnum.DELETE, bookDeletedMessage);
    }

    public String messagePattern(MessageTypeEnum type, String className) {
        Map<MessageTypeEnum, String> resultMap;
        switch (className) {
            case "Author" -> resultMap = authorMap;
            case "Book" -> resultMap = bookMap;
            default -> resultMap = Collections.emptyMap();
        }

        return resultMap.getOrDefault(type, "");
    }

}
