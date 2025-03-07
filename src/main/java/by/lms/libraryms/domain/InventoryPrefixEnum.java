package by.lms.libraryms.domain;

import lombok.Getter;

@Getter
public enum InventoryPrefixEnum {
    MIN('-'),
    MOG('-'),
    GOM('-'),
    GRO('-'),
    VIT('-'),
    BRE('-');

    private final char delimiter;
    
    InventoryPrefixEnum(char delimiter) {
        this.delimiter = delimiter;
    }
}
