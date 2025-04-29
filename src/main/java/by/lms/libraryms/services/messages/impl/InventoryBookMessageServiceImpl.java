package by.lms.libraryms.services.messages.impl;

import by.lms.libraryms.conf.i18n.MessageConf;
import by.lms.libraryms.conf.i18n.MessageTypeEnum;
import by.lms.libraryms.dto.req.BookDTO;
import by.lms.libraryms.dto.req.InventoryBookDTO;
import by.lms.libraryms.dto.resp.ObjectChangedDTO;
import by.lms.libraryms.mappers.InventoryBookMapper;
import by.lms.libraryms.services.messages.BookMessageService;
import by.lms.libraryms.services.messages.InventoryBookMessageService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InventoryBookMessageServiceImpl extends AbstractMessageServiceImpl<InventoryBookDTO> implements InventoryBookMessageService {
    private final BookMessageService bookMessageService;
    private final InventoryBookMapper inventoryBookMapper;

    public InventoryBookMessageServiceImpl(MessageConf messageConf,
                                           BookMessageService bookMessageService,
                                           InventoryBookMapper inventoryBookMapper) {
        super(messageConf);
        this.bookMessageService = bookMessageService;
        this.inventoryBookMapper = inventoryBookMapper;
    }

    @Override
    public void addSpecific(MessageTypeEnum typeEnum, ObjectChangedDTO<InventoryBookDTO> dto, List<Object> args) {
        ObjectChangedDTO<BookDTO> bookChangedDTO = inventoryBookMapper.toBookChangedDTO(dto);
        InventoryBookDTO inventoryBookDTO = dto.getObject();
        args.add(inventoryBookDTO.getInventoryNumber());
        bookMessageService.addSpecific(typeEnum, bookChangedDTO, args);
    }

    @Override
    protected String getPattern(MessageTypeEnum typeEnum) {
        return messageConf().getInventoryBookMap().getOrDefault(typeEnum, "");
    }
}
