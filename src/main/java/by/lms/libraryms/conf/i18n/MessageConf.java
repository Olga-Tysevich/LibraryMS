package by.lms.libraryms.conf.i18n;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class MessageConf {
    @Value("{author.created.message}")
    private String authorCreatedMessage;
    @Value("{author.updated.message}")
    private String authorUpdatedMessage;
    @Value("{author.deleted.message}")
    private String authorDeletedMessage;
}
