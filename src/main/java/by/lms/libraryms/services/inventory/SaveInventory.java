package by.lms.libraryms.services.inventory;

import by.lms.libraryms.domain.Inventory;

@FunctionalInterface
public interface SaveInventory {
    Inventory save(Inventory inventory);
}
