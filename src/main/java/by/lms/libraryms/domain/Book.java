package by.lms.libraryms.domain;

import by.lms.libraryms.utils.Constants;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "books")
public class Book extends AbstractDomainClass {
    @NotEmpty(message = Constants.EMPTY_TITLE_MESSAGE)
    private String title;
    @NotNull
    private Set<ObjectId> authorIds;
    @NotNull
    @Size(min = 1, message = Constants.EMPTY_AUTHOR_MESSAGE)
    private Set<ObjectId> genreIds;
    @NotNull
    @Size(min = 1, message = Constants.EMPTY_AUTHOR_MESSAGE)
    private int year;
}
