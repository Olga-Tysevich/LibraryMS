package by.lms.libraryms.services.searchobjects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Sort;

import java.time.Instant;
import java.util.List;

@SuperBuilder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class SearchReq {
    private List<String> id;
    private Instant createdAtFrom;
    private Instant createdAtTo;
    private Instant updatedAtFrom;
    private Instant updatedAtTO;
    private Integer pageNum;
    private Integer pageSize;
    private Sort.Direction direction;
    private Sort.Order orderBy;
}
