package by.lms.libraryms.services.messages;

import by.lms.libraryms.conf.i18n.MessageTypeEnum;
import by.lms.libraryms.dto.req.InventoryBookDTO;
import by.lms.libraryms.dto.resp.ObjectChangedDTO;

import java.util.List;

public interface InventoryBookMessageService extends MessageService<InventoryBookDTO> {
    void addSpecific(MessageTypeEnum typeEnum, ObjectChangedDTO<InventoryBookDTO> dto, List<Object> args);
}
