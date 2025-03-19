package by.lms.libraryms.services;

import by.lms.libraryms.domain.InventoryBook;
import by.lms.libraryms.dto.req.InventoryBookDTO;
import by.lms.libraryms.dto.req.InventoryBookSearchReqDTO;
import by.lms.libraryms.dto.resp.ObjectListChangedDTO;
import by.lms.libraryms.mappers.InventoryBookMapper;
import by.lms.libraryms.services.searchobjects.InventoryBookSearchReq;
import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public interface InventoryBookService extends AbstractService<InventoryBook, InventoryBookDTO,
        InventoryBookSearchReq, InventoryBookSearchReqDTO,
        InventoryBookMapper> {
}
