package by.lms.libraryms.services.messages;

import by.lms.libraryms.conf.i18n.MessageTypeEnum;
import by.lms.libraryms.dto.req.BookDTO;
import by.lms.libraryms.dto.resp.ObjectChangedDTO;

import java.util.List;

public interface BookMessageService extends MessageService<BookDTO> {
    void addSpecific(MessageTypeEnum typeEnum, ObjectChangedDTO<BookDTO> dto, List<Object> args);
}
