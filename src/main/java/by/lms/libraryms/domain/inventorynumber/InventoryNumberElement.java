package by.lms.libraryms.domain.inventorynumber;

public record InventoryNumberElement(Integer number) {
    public InventoryNumberElement {
        if (number < 0 || String.valueOf(number).length() > 4) {
            throw new IllegalArgumentException("Number cannot be greater than 4 digits.");
        }
    }
}
