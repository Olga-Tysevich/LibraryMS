package by.lms.libraryms.domain;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "book_issues")
public class BookOrder extends AbstractDomainClass {
    @NotNull
    private ObjectId inventoryBookId;
    @NotNull
    private ObjectId userId;
    @NotNull
    private ObjectId librarianId;
    @NotNull
    private LocalDate returnDate;
    private int periodOfDelay;
    private String comment;
}
