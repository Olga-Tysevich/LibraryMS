package by.lms.libraryms.facades;

import by.lms.libraryms.domain.InventoryBook;
import by.lms.libraryms.dto.req.InventoryBookDTO;
import by.lms.libraryms.dto.req.InventoryBookSearchReqDTO;
import by.lms.libraryms.mappers.InventoryBookMapper;
import by.lms.libraryms.services.InventoryBookService;
import by.lms.libraryms.services.messages.InventoryBookMessageService;
import by.lms.libraryms.services.searchobjects.InventoryBookSearchReq;

public interface InventoryBookFacade extends AbstractFacade<InventoryBook, InventoryBookDTO,
        InventoryBookSearchReq, InventoryBookSearchReqDTO,
        InventoryBookService, InventoryBookMessageService,
        InventoryBookMapper> {
}
