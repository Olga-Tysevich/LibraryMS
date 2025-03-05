package by.lms.libraryms.dto.req;

import by.lms.libraryms.dto.SearchReqDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BookSearchReqDTO extends SearchReqDTO {
    private List<String> titles;
    private List<Integer> years;
    private Integer yearFrom;
    private Integer yearTo;
    private Set<ObjectId> authorIds;
    private Set<ObjectId> genreIds;
}
