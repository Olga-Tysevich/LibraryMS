package by.lms.libraryms.dto.resp;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ObjectChangedDTO {
    @NotEmpty(message = "{validation.object.id.empty}")
    private String id;
    @NotEmpty(message = "{validation.object.class.empty}")
    private String objectClass;
    @NotNull(message = "{validation.object.date.null}")
    private LocalDateTime createdAt;
    @NotNull(message = "{validation.object.date.null}")
    private LocalDateTime updatedAt;
    @NotNull(message = "{validation.object.date.null}")
    private LocalDateTime deletedAt;
}
