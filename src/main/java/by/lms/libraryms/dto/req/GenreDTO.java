package by.lms.libraryms.dto.req;

import by.lms.libraryms.dto.AbstractDTO;
import by.lms.libraryms.utils.Constants;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenreDTO extends AbstractDTO {
    @NotBlank(message = Constants.EMPTY_NAME_MESSAGE)
    private String name;
}
