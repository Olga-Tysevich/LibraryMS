package by.lms.libraryms.dto.req;

import by.lms.libraryms.dto.AbstractDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import java.time.LocalDate;
import java.time.Period;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookLendingDTO extends AbstractDTO {
    @NotBlank
    private String inventoryBookId;
    @NotBlank
    private String readerId;
    @NotBlank
    private String librarianId;
    @NotNull
    private LocalDate requiredReturnDate;
    private LocalDate actualReturnDate;
    private Period periodOfDelay;
    private String comment;
}
