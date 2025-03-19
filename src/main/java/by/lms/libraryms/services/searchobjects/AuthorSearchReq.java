package by.lms.libraryms.services.searchobjects;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorSearchReq extends SearchReq {
    private Set<String> fullNames;
}
