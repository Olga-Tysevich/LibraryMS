package by.lms.libraryms.services;

import by.lms.libraryms.domain.inventorynumber.Inventory;
import by.lms.libraryms.domain.inventorynumber.InventoryNumber;
import by.lms.libraryms.dto.resp.InventoryNumberDTO;
import by.lms.libraryms.exceptions.BindingInventoryNumberException;
import by.lms.libraryms.exceptions.UnbindInventoryNumberException;
import by.lms.libraryms.services.searchobjects.InventoryNumberSearchReq;
import by.lms.libraryms.services.searchobjects.ListForPageResp;
import org.springframework.lang.NonNull;


public interface InventoryNumberService {
    InventoryNumber getLastNumber();
    Inventory bind(@NonNull Inventory inventory) throws BindingInventoryNumberException;

    void unbind(@NonNull Inventory relatedObject) throws UnbindInventoryNumberException;

    InventoryNumber dispose(@NonNull InventoryNumber number);

    InventoryNumberDTO get(@NonNull String id);
    InventoryNumberDTO getById(@NonNull String id);

    ListForPageResp<InventoryNumberDTO> getAll(@NonNull InventoryNumberSearchReq searchReq);
}
