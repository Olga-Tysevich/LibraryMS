package by.lms.libraryms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class SearchReqDTO {
    private String id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer pageNum;
    private Integer pageSize;
    private String direction;
    private String orderBy;
}
