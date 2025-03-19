package by.lms.libraryms.dto.req;

import by.lms.libraryms.dto.SearchReqDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockBookSearchReqDTO extends SearchReqDTO {
    private Set<String> inventoryBookIds;
    private Set<String> titles;
    private Set<String> authorIds;
    private Set<String> genreIds;
    private Set<Integer> years;
    private Integer yearFrom;
    private Integer yearTo;
    private Set<String> bookIds;
    private Map<String, Integer> numberOfBooks;
    private Set<String> inventoryNumbers;
    private LocalDate dateOfReceiptFrom;
    private LocalDate dateOfReceiptTo;
}
