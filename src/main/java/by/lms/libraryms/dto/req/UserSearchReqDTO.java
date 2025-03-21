package by.lms.libraryms.dto.req;

import by.lms.libraryms.dto.SearchReqDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSearchReqDTO extends SearchReqDTO {
    private Set<String> usernames;
    private Set<String> emails;
    private Set<String> fullNames;
    private Set<String> phones;
    private Set<String> fullAddresses;
    private Set<Integer> roles;
}
