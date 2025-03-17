package by.lms.libraryms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class SearchReqDTO {
    private Set<String> ids;
    private LocalDateTime createdAtFrom;
    private LocalDateTime createdAtTo;
    private LocalDateTime updatedAtFrom;
    private LocalDateTime updatedAtTo;
    private Integer pageNum;
    private Integer pageSize;
    private String direction;
    private String orderBy;
}
