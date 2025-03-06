package by.lms.libraryms.conf.i18n;

import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
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

    @Getter
    private Map<MessageTypeEnum, String> authorMap;
    @Getter
    private Map<MessageTypeEnum, String> bookMap;


    @PostConstruct
    public void init() {
        authorMap = convertToEnumMap(author);
        bookMap = convertToEnumMap(book);
    }

    private Map<MessageTypeEnum, String> convertToEnumMap(Map<String, String> source) {
        return source.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> MessageTypeEnum.valueOf(entry.getKey().toUpperCase()),
                        Map.Entry::getValue
                ));
    }

}
