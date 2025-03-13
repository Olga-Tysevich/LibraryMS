package by.lms.libraryms.services.searchobjects;

import by.lms.libraryms.domain.inventorynumber.InventoryNumberElement;
import by.lms.libraryms.domain.inventorynumber.InventoryPrefixEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryNumberSearchReq extends SearchReq {
    private List<InventoryPrefixEnum> prefixes;
    private List<InventoryNumberElement> numbers;
    private Boolean isDisposedOf;
    private LocalDate disposedDateFrom;
    private LocalDate disposedDateTo;
}
