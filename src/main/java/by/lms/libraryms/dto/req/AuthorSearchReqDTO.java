package by.lms.libraryms.dto.req;

import by.lms.libraryms.dto.SearchReqDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AuthorSearchReqDTO extends SearchReqDTO {
    private List<String> fullNames;
}
