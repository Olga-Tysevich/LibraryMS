package by.lms.libraryms.dto.req;

import by.lms.libraryms.dto.AbstractDTO;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDTO extends AbstractDTO {
    @NotEmpty(message = "{validation.author.name.empty}")
    private String name;
    @NotEmpty(message = "{validation.author.surname.empty}")
    private String surname;
}
