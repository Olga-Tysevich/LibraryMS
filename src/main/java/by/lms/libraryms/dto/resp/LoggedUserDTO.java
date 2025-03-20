package by.lms.libraryms.dto.resp;

import by.lms.libraryms.dto.common.UserDTO;
import by.lms.libraryms.utils.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A data transfer object (DTO) class representing a logged-in user.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoggedUserDTO {
    /**
     * The type of authentication token (e.g., Bearer).
     */
    @Builder.Default
    private String type = Constants.TOKEN_TYPE;
    /**
     * The access token for the logged-in user.
     */
    private String accessToken;
    /**
     * The refresh token for the logged-in user.
     */
    private String refreshToken;
    /**
     * The user data associated with the logged-in user.
     */
    private UserDTO userDTO;

}