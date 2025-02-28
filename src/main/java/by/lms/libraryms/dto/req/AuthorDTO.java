package by.lms.libraryms.dto.req;

import by.lms.libraryms.dto.AbstractDTO;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDTO extends AbstractDTO {
    @NotEmpty(message = "{validation.object.name.empty}")
    private String name;
    @NotEmpty(message = "{validation.object.surname.empty}")
    private String surname;
}
