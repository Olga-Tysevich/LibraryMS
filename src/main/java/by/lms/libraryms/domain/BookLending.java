package by.lms.libraryms.domain;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@Document(collection = "book_lendings")
public class BookLending extends AbstractDomainClass {
    @NotNull
    private ObjectId inventoryBookId;
    @NotNull
    private ObjectId readerId;
    @NotNull
    private ObjectId librarianId;
    @NotNull
    private LocalDate returnDate;
    private int periodOfDelay;
    private String comment;
}
