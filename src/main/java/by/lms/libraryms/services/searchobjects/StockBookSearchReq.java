package by.lms.libraryms.services.searchobjects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;

import java.time.Instant;
import java.util.Map;
import java.util.Set;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockBookSearchReq extends SearchReq {
    private Set<String> titles;
    private Set<ObjectId> authorIds;
    private Set<ObjectId> genreIds;
    private Set<Integer> years;
    private Integer yearFrom;
    private Integer yearTo;
    private Set<ObjectId> bookIds;
    private Map<ObjectId, Integer> numberOfBooks;
    private Set<ObjectId> inventoryNumbers;
    private Instant dateOfReceiptFrom;
    private Instant dateOfReceiptTo;
}
