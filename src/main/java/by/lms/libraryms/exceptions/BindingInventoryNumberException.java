package by.lms.libraryms.exceptions;

import by.lms.libraryms.domain.inventorynumber.Inventory;

public class BindingInventoryNumberException extends Exception {
    public BindingInventoryNumberException(Inventory relatedObject, String exceptionMessage) {
        super(String.format("Binding of the Inventory Number failed for object: %s. Cause: %s",
                relatedObject, exceptionMessage));
    }
}
