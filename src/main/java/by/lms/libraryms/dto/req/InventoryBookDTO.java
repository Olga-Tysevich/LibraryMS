package by.lms.libraryms.dto.req;

import by.lms.libraryms.dto.AbstractDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryBookDTO extends AbstractDTO {
    private BookDTO book;
    private String inventoryNumber;
    private Set<String> bookOrderIds;
    private boolean isAvailable;
    private LocalDate dateOfReceipt;
}
