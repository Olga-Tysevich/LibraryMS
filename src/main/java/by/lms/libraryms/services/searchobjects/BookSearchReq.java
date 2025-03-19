package by.lms.libraryms.services.searchobjects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import java.util.Set;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookSearchReq extends SearchReq {
    private Set<String> titles;
    private Set<Integer> years;
    private Integer yearFrom;
    private Integer yearTo;
    private Set<ObjectId> authorIds;
    private Set<ObjectId> genreIds;
}
