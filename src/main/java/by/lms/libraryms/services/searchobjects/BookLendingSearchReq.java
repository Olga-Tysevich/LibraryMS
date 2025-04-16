package by.lms.libraryms.services.searchobjects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.time.Period;
import java.util.Set;
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookLendingSearchReq extends SearchReq{
    private Set<ObjectId> inventoryBookIds;
    private Set<ObjectId> readerIds;
    private Set<ObjectId> librarianIds;
    private LocalDate requiredReturnDateFrom;
    private LocalDate requiredReturnDateTo;
    private LocalDate actualReturnDateFrom;
    private LocalDate actualReturnDateTo;
    private Period periodOfDelayFrom;
    private Period periodOfDelayTo;
    private Set<String> commentKeywords;
}
