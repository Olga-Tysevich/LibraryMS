package by.lms.libraryms.conf.i18n;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

//TODO разобраться с @PropertySource
@Getter
@Configuration
@PropertySource("classpath:by/lms/libraryms/i18n/messages.properties")
public class MessageConf {
    @Value("${author.created.message}")
    private String authorCreatedMessage;
    @Value("${author.updated.message}")
    private String authorUpdatedMessage;
    @Value("${author.deleted.message}")
    private String authorDeletedMessage;
}
