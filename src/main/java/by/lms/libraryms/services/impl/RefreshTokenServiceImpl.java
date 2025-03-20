package by.lms.libraryms.services.impl;

import by.lms.libraryms.conf.auth.AESEncryptor;
import by.lms.libraryms.domain.auth.RefreshToken;
import by.lms.libraryms.domain.auth.User;
import by.lms.libraryms.repo.RefreshTokenRepo;
import by.lms.libraryms.services.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

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
     * AESEncryptor bean.
     *
     * @see AESEncryptor
     */
    private final AESEncryptor aesEncryptor;

    /**
     * Method to save a new refresh token to database.
     *
     * @param token The refresh token object to save to the database.
     * @param userId The User id of the token owner.
     * @see RefreshToken
     */
    @Override
    public void save(String token, String userId) {
        String encryptedToken = aesEncryptor.encrypt(token);
        RefreshToken refreshToken = RefreshToken.builder()
                .tokenValue(encryptedToken)
                .userId(new ObjectId())
                .build();
        refreshTokenRepository.save(refreshToken);
    }


    @Override
    public Set<String> getUserRefreshTokens(String userId) {
        return refreshTokenRepository.findAllByUserId(new ObjectId(userId)).stream()
                .map(RefreshToken::getTokenValue)
                .map(aesEncryptor::decrypt)
                .collect(Collectors.toSet());
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

    @Override
    public void removeRefreshToken(String token) {
        String encryptedToken = aesEncryptor.encrypt(token);
        refreshTokenRepository.deleteByTokenValue(encryptedToken);
    }
}