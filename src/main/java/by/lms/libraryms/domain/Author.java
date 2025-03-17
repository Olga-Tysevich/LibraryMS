package by.lms.libraryms.domain;

import by.lms.libraryms.utils.Constants;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "authors")
public class Author extends AbstractDomainClass {
    @NotEmpty(message = Constants.EMPTY_NAME_MESSAGE)
    private String name;
    @NotEmpty(message =  Constants.EMPTY_SURNAME_MESSAGE)
    private String surname;
}
