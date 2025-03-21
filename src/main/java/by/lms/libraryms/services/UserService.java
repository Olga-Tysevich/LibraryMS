package by.lms.libraryms.services;

import by.lms.libraryms.dto.common.UserDTO;
import jakarta.validation.constraints.NotEmpty;

public interface UserService {

    UserDTO findByEmail(@NotEmpty String email);

}
