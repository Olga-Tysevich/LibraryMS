package by.lms.libraryms.dto;

import by.lms.libraryms.utils.Constants;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public abstract class AbstractDTO {
    @NotEmpty(message = Constants.EMPTY_ID_MESSAGE)
    private String id;
    @NotNull(message = Constants.DATE_IS_NULL_MESSAGE)
    private LocalDateTime createdAt;
    @NotNull(message = Constants.DATE_IS_NULL_MESSAGE)
    private LocalDateTime updatedAt;
}
