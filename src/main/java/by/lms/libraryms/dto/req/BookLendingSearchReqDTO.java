package by.lms.libraryms.dto.req;

import by.lms.libraryms.dto.SearchReqDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.Period;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BookLendingSearchReqDTO  extends SearchReqDTO {
    private Set<String> inventoryBookIds;
    private Set<String> readerIds;
    private Set<String> librarianIds;
    private LocalDate requiredReturnDateFrom;
    private LocalDate requiredReturnDateTo;
    private LocalDate actualReturnDateFrom;
    private LocalDate actualReturnDateTo;
    private Period periodOfDelayFrom;
    private Period periodOfDelayTo;
    private Set<String> commentKeywords;
}
