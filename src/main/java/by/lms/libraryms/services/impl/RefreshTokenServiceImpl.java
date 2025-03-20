package by.lms.libraryms.services.impl;

import by.lms.libraryms.domain.auth.RefreshToken;
import by.lms.libraryms.domain.auth.User;
import by.lms.libraryms.repo.RefreshTokenRepo;
import by.lms.libraryms.services.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class that provides implementation for generating and regenerating JWT tokens.
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRED)
public class RefreshTokenServiceImpl implements RefreshTokenService {
    /**
     * RefreshTokenRepository bean.
     *
     * @see RefreshTokenRepo
     */
    private final RefreshTokenRepo refreshTokenRepository;

    /**
     * Method to save a new refresh token to database.
     *
     * @param token The refresh token object to save to the database.
     * @param userId The User id of the token owner.
     * @see RefreshToken
     */
    @Override
    public void save(String token, String userId) {
        RefreshToken refreshToken = RefreshToken.builder()
                .tokenValue(token)
                .userId(new ObjectId())
                .build();
        refreshTokenRepository.save(refreshToken);
    }


    /**
     * Method to block all refresh tokens for a given user.
     *
     * @param userId The User id of the token owner.
     * @see User
     * @see RefreshToken
     */
    @Override
    public void removeUserRefreshTokens(String userId) {
        refreshTokenRepository.deleteByUserId(new ObjectId(userId));
    }
}