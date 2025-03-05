package by.lms.libraryms.dto.resp;

import by.lms.libraryms.dto.req.AuthorDTO;
import by.lms.libraryms.utils.Constants;
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
public class ObjectChangedDTO<T> {
    @NotEmpty(message = Constants.EMPTY_ID_MESSAGE)
    private String id;
    @NotEmpty(message = Constants.EMPTY_OBJECT_CLASS_MESSAGE)
    private String objectClass;
    @NotNull(message = Constants.DATE_IS_NULL_MESSAGE)
    private LocalDateTime createdAt;
    @NotNull(message = Constants.DATE_IS_NULL_MESSAGE)
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private T object;
}
