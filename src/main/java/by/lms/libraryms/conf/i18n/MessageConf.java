package by.lms.libraryms.conf.i18n;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.HashMap;
import java.util.Map;

//TODO разобраться с @PropertySource
@Configuration
@PropertySource("classpath:by/lms/libraryms/i18n/messages.properties")
public class MessageConf {
    @Value("${author.created.message}")
    private String authorCreatedMessage;
    @Value("${author.updated.message}")
    private String authorUpdatedMessage;
    @Value("${author.deleted.message}")
    private String authorDeletedMessage;

    @Getter
    private final Map<MessageTypeEnum, String> authorMap = new HashMap<>();

    {
        authorMap.put(MessageTypeEnum.ADD, authorCreatedMessage);
        authorMap.put(MessageTypeEnum.UPDATE, authorUpdatedMessage);
        authorMap.put(MessageTypeEnum.DELETE, authorDeletedMessage);
    }

}
