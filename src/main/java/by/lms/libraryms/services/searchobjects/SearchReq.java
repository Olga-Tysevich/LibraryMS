package by.lms.libraryms.services.searchobjects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Sort;

import java.time.Instant;

@SuperBuilder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class SearchReq {
    private String id;
    private Instant createdAt;
    private Instant updatedAt;
    private Integer pageNum;
    private Integer pageSize;
    private Sort.Direction direction;
    private Sort.Order orderBy;
}
