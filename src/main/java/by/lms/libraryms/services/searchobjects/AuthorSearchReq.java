package by.lms.libraryms.services.searchobjects;

import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorSearchReq extends SearchReq {
    private String name;
    private String surname;
}
