package by.lms.libraryms.services.searchobjects;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorSearchReq extends SearchReq {
    private List<String> fullNames;
}
