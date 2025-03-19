package by.lms.libraryms.dto.req;

import by.lms.libraryms.dto.SearchReqDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BookSearchReqDTO extends SearchReqDTO {
    private Set<String> titles;
    private Set<Integer> years;
    private Integer yearFrom;
    private Integer yearTo;
    private Set<String> authorIds;
    private Set<String> genreIds;
}
