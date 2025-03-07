package by.lms.libraryms.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "genres")
public class Genre extends AbstractDomainClass {
    @Indexed(unique = true)
    private String name;
}
