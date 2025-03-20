package by.lms.libraryms.services;


import by.lms.libraryms.dto.req.UserLoginDTO;
import by.lms.libraryms.dto.resp.LoggedUserDTO;

/**
 * Interface for authenticating and logging in users.
 */
public interface AuthService {

    /**
     * Attempts to log in a user with the provided login information.
     *
     * @param req the user login information.
     * @return a DTO containing the details of the logged-in user.
     * @see UserLoginDTO
     * @see LoggedUserDTO
     */
    LoggedUserDTO loginUser(UserLoginDTO req);

    /**
     * Attempts to re-login a user using a refresh token.
     *
     * @param refreshToken the refresh token for re-authentication.
     * @return a DTO containing the details of the re-logged in user.
     * @see LoggedUserDTO
     */
    LoggedUserDTO reLoginUser(String refreshToken);
}