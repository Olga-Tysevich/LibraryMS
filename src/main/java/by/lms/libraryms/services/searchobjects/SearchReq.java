package by.lms.libraryms.services.searchobjects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;

import java.time.Instant;
import java.util.Set;

@SuperBuilder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class SearchReq {
    private Set<ObjectId> ids;
    private Instant createdAtFrom;
    private Instant createdAtTo;
    private Instant updatedAtFrom;
    private Instant updatedAtTo;
    private Integer pageNum;
    private Integer pageSize;
    private Sort.Direction direction;
    private Sort.Order orderBy;
}
