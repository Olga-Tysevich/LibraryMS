package by.lms.libraryms.services;

import by.lms.libraryms.domain.Inventory;
import by.lms.libraryms.domain.InventoryNumber;
import by.lms.libraryms.dto.resp.InventoryNumberDTO;
import by.lms.libraryms.exceptions.BindingInventoryNumberException;
import by.lms.libraryms.exceptions.UnbindInventoryNumberException;
import by.lms.libraryms.services.searchobjects.InventoryNumberSearchReq;
import by.lms.libraryms.services.searchobjects.ListForPageResp;
import org.springframework.lang.NonNull;


public interface InventoryNumberService{
    Inventory add(@NonNull Inventory inventory) throws BindingInventoryNumberException;
    void unbind(@NonNull Inventory relatedObject) throws UnbindInventoryNumberException;
    InventoryNumber dispose(@NonNull InventoryNumber number);
    InventoryNumberDTO get(@NonNull String id);
    ListForPageResp<InventoryNumberDTO> getAll(@NonNull InventoryNumberSearchReq searchReq);
}
