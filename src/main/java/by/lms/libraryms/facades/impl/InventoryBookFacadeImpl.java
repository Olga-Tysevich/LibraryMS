package by.lms.libraryms.facades.impl;

import by.lms.libraryms.domain.InventoryBook;
import by.lms.libraryms.dto.req.InventoryBookDTO;
import by.lms.libraryms.dto.req.InventoryBookSearchReqDTO;
import by.lms.libraryms.dto.resp.ObjectChangedDTO;
import by.lms.libraryms.dto.resp.ObjectListChangedDTO;
import by.lms.libraryms.exceptions.ActionProhibitedException;
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

    //TODO добавить данные про аккаунт библиотекаря
    @Override
    public ObjectListChangedDTO<InventoryBookDTO> delete(InventoryBookSearchReqDTO searchReqDTO) {
        throw new ActionProhibitedException(String.format("Deleting inventory books is prohibited! Account: %s Inventory book: %s",
                "", searchReqDTO));
    }

    //TODO добавить данные про аккаунт библиотекаря
    @Override
    public ObjectChangedDTO<InventoryBookDTO> add(InventoryBookDTO dto) {
        throw new ActionProhibitedException(String.format("Adding inventory books directly is prohibited! Account: %s Inventory book: %s",
                "", dto));
    }
}
