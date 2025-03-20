package by.lms.libraryms.services;

import by.lms.libraryms.domain.auth.User;

/**
 * The interface for managing JWT refresh tokens.
 */
public interface RefreshTokenService {

    /**
     * Method to save a new refresh token to database.
     *
     * @param token The refresh token object to save to the database.
     * @param userId The User id of the token owner.
     * @see by.lms.libraryms.domain.auth.RefreshToken
     */
    void save(String token, String userId);

    /**
     * Method to block all refresh tokens for a given user id.
     *
     * @param userId The User id of the token owner.
     * @see User
     * @see by.lms.libraryms.domain.auth.RefreshToken
     */
    void removeUserRefreshTokens(String userId);

}