package by.lms.libraryms.dto.req;

import by.lms.libraryms.dto.SearchReqDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryBookSearchReqDTO extends SearchReqDTO {
    private List<String> titles;
    private Set<String> authorIds;
    private Set<String> genreIds;
    private List<Integer> years;
    private Integer yearFrom;
    private Integer yearTo;
    private List<String> inventoryNumbers;
    private Set<String> bookOrderIds;
    private Boolean isAvailable;
    private LocalDate dateOfReceiptFrom;
    private LocalDate dateOfReceiptTo;
}
