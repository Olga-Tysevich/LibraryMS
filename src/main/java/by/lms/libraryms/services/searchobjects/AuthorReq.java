package by.lms.libraryms.services.searchobjects;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AuthorReq extends SearchReq {
    private String name;
    private String surname;
}
