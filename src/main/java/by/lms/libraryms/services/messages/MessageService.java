package by.lms.libraryms.services.messages;

import by.lms.libraryms.conf.i18n.MessageTypeEnum;
import by.lms.libraryms.dto.resp.ObjectChangedDTO;
import by.lms.libraryms.dto.resp.ObjectListChangedDTO;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageService<T> {
    Message createMessage(@NotNull String pattern, @NotNull LocalDateTime dateTime, @NotNull Object... args);
    Message createMessage(@NotNull MessageTypeEnum typeEnum, @NotNull ObjectChangedDTO<T> object);
    List<Message> createMessages(@NotNull MessageTypeEnum typeEnum, @NotNull ObjectListChangedDTO<T> object);
}
