package by.lms.libraryms.dto.req;

import by.lms.libraryms.dto.AbstractDTO;
import by.lms.libraryms.utils.Constants;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDTO extends AbstractDTO {
    @NotEmpty(message = Constants.EMPTY_NAME_MESSAGE)
    private String name;
    @NotEmpty(message = Constants.EMPTY_SURNAME_MESSAGE)
    private String surname;
}
