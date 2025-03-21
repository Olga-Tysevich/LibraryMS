package by.lms.libraryms.services.searchobjects;

import by.lms.libraryms.domain.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSearchReq extends SearchReq {
    private Set<String> usernames;
    private Set<String> emails;
    private Map<String, String> fullNames;
    private Set<String> phones;
    private Set<ObjectId> addressIds;
    private EnumSet<RoleEnum> roles;
}
