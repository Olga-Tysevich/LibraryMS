package by.lms.libraryms.dto;

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
    @NotEmpty(message = "{validation.object.id.empty}")
    private String id;
    @NotNull(message = "{validation.object.date.null}")
    private LocalDateTime createdAt;
    @NotNull(message = "{validation.object.date.null}")
    private LocalDateTime updatedAt;
}
