package by.lms.libraryms.services.searchreq;

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
    private int pageNum;
    private int pageSize;
    private Sort.Direction direction;
    private String orderBy;
}
