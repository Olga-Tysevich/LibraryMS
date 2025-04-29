package by.lms.libraryms.conf.i18n;

import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Map;
import java.util.stream.Collectors;

//TODO разобраться с @PropertySource
@Configuration
@PropertySource("classpath:by/lms/libraryms/i18n/messages.properties")
@ConfigurationProperties(prefix = "message")
public class MessageConf {
    @Getter(AccessLevel.PACKAGE)
    @Setter(AccessLevel.PACKAGE)
    private Map<String, String> author;
    @Getter(AccessLevel.PACKAGE)
    @Setter(AccessLevel.PACKAGE)
    private Map<String, String> book;
    @Getter(AccessLevel.PACKAGE)
    @Setter(AccessLevel.PACKAGE)
    private Map<String, String> genre;
    @Getter(AccessLevel.PACKAGE)
    @Setter(AccessLevel.PACKAGE)
    private Map<String, String> inventoryBook;
    @Getter(AccessLevel.PACKAGE)
    @Setter(AccessLevel.PACKAGE)
    private Map<String, String> stockBook;
    @Getter(AccessLevel.PACKAGE)
    @Setter(AccessLevel.PACKAGE)
    private Map<String, String> bookLending;

    @Getter
    private Map<MessageTypeEnum, String> authorMap;
    @Getter
    private Map<MessageTypeEnum, String> bookMap;
    @Getter
    private Map<MessageTypeEnum, String> genreMap;
    @Getter
    private Map<MessageTypeEnum, String> inventoryBookMap;
    @Getter
    private Map<MessageTypeEnum, String> stockBookMap;
    @Getter
    private Map<MessageTypeEnum, String> bookLendingMap;

    @Getter
    @Value("emailConfirmation")
    private String emailConfirmation;
    @Getter
    @Value("emailConfirmationSubject")
    private String emailConfirmationSubject;


    @PostConstruct
    public void init() {
        authorMap = convertToEnumMap(author);
        bookMap = convertToEnumMap(book);
        genreMap = convertToEnumMap(genre);
        inventoryBookMap = convertToEnumMap(inventoryBook);
        stockBookMap = convertToEnumMap(stockBook);
        bookLendingMap = convertToEnumMap(bookLending);
    }

    private Map<MessageTypeEnum, String> convertToEnumMap(Map<String, String> source) {
        return source.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> MessageTypeEnum.valueOf(entry.getKey().toUpperCase()),
                        Map.Entry::getValue
                ));
    }

}
