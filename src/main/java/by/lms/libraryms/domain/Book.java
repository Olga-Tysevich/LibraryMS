package by.lms.libraryms.domain;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "books")
public class Book extends AbstractDomainClass {
    @NotNull
    private String title;
    @NotNull
    private Set<ObjectId> authorIds;
    @NotNull
    private Set<ObjectId> genreId;
    @NotNull
    private int year;
    private boolean available = true;
}
