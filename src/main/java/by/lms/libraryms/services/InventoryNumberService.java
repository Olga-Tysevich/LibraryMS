package by.lms.libraryms.services;

import by.lms.libraryms.domain.Inventory;
import by.lms.libraryms.domain.InventoryNumber;
import by.lms.libraryms.domain.InventoryPrefixEnum;
import by.lms.libraryms.dto.resp.InventoryNumberDTO;
import by.lms.libraryms.services.inventory.SaveInventory;
import by.lms.libraryms.services.searchobjects.InventoryNumberSearchReq;
import by.lms.libraryms.services.searchobjects.ListForPageResp;
import jakarta.validation.constraints.NotNull;

import java.util.function.Supplier;


public interface InventoryNumberService{
    Inventory setRelated(@NotNull Inventory entity, @NotNull InventoryPrefixEnum prefix, @NotNull SaveInventory saveMethod);
    InventoryNumber dispose(@NotNull InventoryNumber number);
    InventoryNumberDTO get(@NotNull String id);
    ListForPageResp<InventoryNumberDTO> getAll(@NotNull InventoryNumberSearchReq searchReq);
}
