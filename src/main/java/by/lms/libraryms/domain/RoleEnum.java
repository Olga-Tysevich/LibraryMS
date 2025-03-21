package by.lms.libraryms.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RoleEnum {
    ROLE_ADMIN(1, "ADMIN"),
    ROLE_USER(2, "USER"),
    ROLE_READER(3, "READER"),
    ROLE_LIBRARIAN(4, "LIBRARIAN");

    private final int id;
    private final String roleName;

    public static RoleEnum fromId(int id) {
        return RoleEnum.values()[id];
    }

}
