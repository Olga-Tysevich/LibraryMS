package by.lms.libraryms.domain;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "authors")
public class Author extends AbstractDomainClass {
    @NotEmpty(message = "{validation.author.name.empty}")
    private String name;
    @NotEmpty(message = "{validation.author.surname.empty}")
    private String surname;
}
