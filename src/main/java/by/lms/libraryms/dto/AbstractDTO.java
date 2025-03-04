package by.lms.libraryms.dto;

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
    private String id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
