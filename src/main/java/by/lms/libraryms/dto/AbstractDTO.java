package by.lms.libraryms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public abstract class AbstractDTO {
    private int id;
    private Instant createdAt;
    private Instant updatedAt;
}
