package by.lms.libraryms.services.searchobjects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Set;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookSearchReq extends SearchReq {
    private List<String> titles;
    private List<Integer> years;
    private Integer yearFrom;
    private Integer yearTo;
    private Set<ObjectId> authorIds;
    private Set<ObjectId> genreIds;
}
