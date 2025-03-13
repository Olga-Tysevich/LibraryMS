package by.lms.libraryms.dto.req;

import by.lms.libraryms.dto.AbstractDTO;
import by.lms.libraryms.utils.Constants;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;


//TODO подумать как искать
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockBookDTO extends AbstractDTO {
    @NotNull(message = Constants.EMPTY_ID_MESSAGE)
    private String bookId;
    private String inventoryNumberId;
    @Positive(message = Constants.INVALID_QUANTITY_MESSAGE)
    private int quantity;
    @NotNull(message = Constants.DATE_IS_NULL_MESSAGE)
    private LocalDate dateOfReceipt;
}
