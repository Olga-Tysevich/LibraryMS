package by.lms.libraryms.services.messages;

import by.lms.libraryms.conf.i18n.MessageTypeEnum;
import by.lms.libraryms.dto.resp.ObjectChangedDTO;

import java.time.LocalDateTime;

public interface MessageService<T> {
    String createMessage(String pattern, LocalDateTime dateTime, Object... args);
    String createMessage(MessageTypeEnum typeEnum, ObjectChangedDTO<T> object);
}
