package by.lms.libraryms.services;

import by.lms.libraryms.domain.InventoryNumber;
import by.lms.libraryms.domain.InventoryPrefixEnum;
import by.lms.libraryms.dto.resp.InventoryNumberDTO;
import by.lms.libraryms.services.searchobjects.InventoryNumberSearchReq;
import by.lms.libraryms.services.searchobjects.ListForPageResp;


public interface InventoryNumberService {
    InventoryNumber add(InventoryPrefixEnum prefix);
    InventoryNumber dispose(InventoryNumber number);
    InventoryNumberDTO get(String id);
    ListForPageResp<InventoryNumberDTO> getAll(InventoryNumberSearchReq searchReq);
}
