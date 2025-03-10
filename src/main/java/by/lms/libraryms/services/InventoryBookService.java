package by.lms.libraryms.services;

import by.lms.libraryms.domain.InventoryBook;
import by.lms.libraryms.dto.req.InventoryBookDTO;
import by.lms.libraryms.dto.req.InventoryBookSearchReqDTO;
import by.lms.libraryms.mappers.InventoryBookMapper;
import by.lms.libraryms.services.searchobjects.InventoryBookSearchReq;

public interface InventoryBookService extends AbstractService<InventoryBook, InventoryBookDTO,
        InventoryBookSearchReq, InventoryBookSearchReqDTO,
        InventoryBookMapper> {
}
