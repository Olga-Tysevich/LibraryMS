package by.lms.libraryms.services.messages.impl;

import by.lms.libraryms.conf.i18n.MessageConf;
import by.lms.libraryms.conf.i18n.MessageTypeEnum;
import by.lms.libraryms.dto.req.BookLendingDTO;
import by.lms.libraryms.dto.req.InventoryBookDTO;
import by.lms.libraryms.dto.resp.ObjectChangedDTO;
import by.lms.libraryms.services.InventoryBookService;
import by.lms.libraryms.services.messages.BookLendingMessageService;
import by.lms.libraryms.services.messages.InventoryBookMessageService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookLendingMessageServiceImpl extends AbstractMessageServiceImpl<BookLendingDTO>
        implements BookLendingMessageService {
    private final InventoryBookMessageService inventoryBookMessageService;
    private final InventoryBookService inventoryBookService;

    public BookLendingMessageServiceImpl(MessageConf messageConf,
                                         InventoryBookMessageService inventoryBookMessageService,
                                         InventoryBookService inventoryBookService) {
        super(messageConf);
        this.inventoryBookMessageService = inventoryBookMessageService;
        this.inventoryBookService = inventoryBookService;
    }

    //TODO разобраться с обрщением к бд
    @Override
    protected void addSpecific(MessageTypeEnum typeEnum, ObjectChangedDTO<BookLendingDTO> dto, List<Object> args) {
        BookLendingDTO bookLendingDTO = dto.getObject();

        args.add(bookLendingDTO.getReaderId());

        InventoryBookDTO inventoryBookDTO = inventoryBookService.findById(bookLendingDTO.getInventoryBookId());
        ObjectChangedDTO<InventoryBookDTO> forAdd = ObjectChangedDTO.<InventoryBookDTO>builder()
                .object(inventoryBookDTO)
                .build();
        inventoryBookMessageService.addSpecific(typeEnum, forAdd, args);
    }

    @Override
    protected String getPattern(MessageTypeEnum typeEnum) {
        return messageConf().getBookLendingMap().getOrDefault(typeEnum, "");
    }
}
