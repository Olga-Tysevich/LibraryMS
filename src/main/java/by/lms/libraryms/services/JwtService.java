package by.lms.libraryms.services;


import by.lms.libraryms.domain.auth.User;
import by.lms.libraryms.dto.common.UserDTO;
import by.lms.libraryms.dto.resp.LoggedUserDTO;

/**
 * The interface for managing JWT tokens.
 */
public interface JwtService {

    /**
     * Generates a pair of JWT tokens (access and refresh tokens) for the specified user.
     *
     * @param userDTO The user data transfer object for whom the tokens are generated.
     * @return A Data Transfer Object (DTO) that contains the generated tokens.
     * @see User
     * @see LoggedUserDTO
     */
    LoggedUserDTO generatePairOfTokens(UserDTO userDTO);

    /**
     * Regenerates a pair of JWT tokens (access and refresh tokens) based on the provided refresh token.
     *
     * @param refreshToken The refresh token used to regenerate the tokens.
     * @return A Data Transfer Object (DTO) that contains the regenerated tokens.
     * @see LoggedUserDTO
     */
    LoggedUserDTO regeneratePairOfTokens(String refreshToken);
}
