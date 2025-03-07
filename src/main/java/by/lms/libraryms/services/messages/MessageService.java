package by.lms.libraryms.services.messages;

import by.lms.libraryms.conf.i18n.MessageTypeEnum;
import by.lms.libraryms.dto.resp.ObjectChangedDTO;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public interface MessageService<T> {
    String createMessage(@NotNull String pattern, @NotNull LocalDateTime dateTime, @NotNull Object... args);
    String createMessage(@NotNull MessageTypeEnum typeEnum, @NotNull ObjectChangedDTO<T> object);
}
