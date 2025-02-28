package by.lms.libraryms.services.searchobjects;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

import java.time.Instant;

@Builder
@Setter
@Getter
public abstract class SearchReq {
    private String id;
    private Instant createdAt;
    private Instant updatedAt;
    private Integer pageNum;
    private Integer pageSize;
    private Sort.Direction direction;
    private Sort.Order orderBy;
}
