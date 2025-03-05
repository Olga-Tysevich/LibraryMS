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
public class BookReq extends SearchReq {
    private String title;
    private Set<ObjectId> authorIds;
    private Set<ObjectId> genreIds;
    private int year;
}
