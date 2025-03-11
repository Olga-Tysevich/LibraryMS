package by.lms.libraryms.facades.impl;

import by.lms.libraryms.domain.InventoryBook;
import by.lms.libraryms.dto.req.InventoryBookDTO;
import by.lms.libraryms.dto.req.InventoryBookSearchReqDTO;
import by.lms.libraryms.facades.InventoryBookFacade;
import by.lms.libraryms.mappers.InventoryBookMapper;
import by.lms.libraryms.services.InventoryBookService;
import by.lms.libraryms.services.NotificationService;
import by.lms.libraryms.services.messages.InventoryBookMessageService;
import by.lms.libraryms.services.searchobjects.InventoryBookSearchReq;
import org.springframework.stereotype.Component;

@Component
public class InventoryBookFacadeImpl extends AbstractFacadeImpl<InventoryBook, InventoryBookDTO,
        InventoryBookSearchReq, InventoryBookSearchReqDTO,
        InventoryBookService, InventoryBookMessageService,
        InventoryBookMapper> implements InventoryBookFacade {

    public InventoryBookFacadeImpl(InventoryBookService service,
                                   NotificationService<InventoryBookDTO> notificationService,
                                   InventoryBookMessageService messageService) {
        super(service, notificationService, messageService);
    }

}
