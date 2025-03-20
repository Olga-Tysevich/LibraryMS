package by.lms.libraryms.services.impl;

import by.lms.libraryms.conf.auth.JwtProvider;
import by.lms.libraryms.dto.common.UserDTO;
import by.lms.libraryms.dto.resp.LoggedUserDTO;
import by.lms.libraryms.exceptions.InvalidRefreshTokenException;
import by.lms.libraryms.services.JwtService;
import by.lms.libraryms.services.RefreshTokenService;
import by.lms.libraryms.services.UserService;
import by.lms.libraryms.utils.Constants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service class that provides implementation for generating and regenerating JWT tokens.
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRED)
public class JwtServiceImpl implements JwtService {
    /**
     * RefreshTokenService bean.
     *
     * @see RefreshTokenService
     */
    private final RefreshTokenService refreshTokenService;
    /**
     * JwtProvider bean.
     *
     * @see JwtProvider
     */
    private final JwtProvider jwtProvider;
    /**
     * UserService bean.
     *
     * @see UserService
     */
    private final UserService userService;

    /**
     * Generates a pair of access and refresh tokens for the given user.
     *
     * @param userDTO The user for whom tokens are generated.
     * @return LoggedUserDTO containing the access token, refresh token, and user details.
     */
    @Override
    public LoggedUserDTO generatePairOfTokens(UserDTO userDTO) {
        String accessToken = jwtProvider.generateAccessToken(userDTO);
        String refreshToken = jwtProvider.generateRefreshToken(userDTO);
        refreshTokenService.save(refreshToken, userDTO.getId());
        return LoggedUserDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userDTO(userDTO)
                .build();
    }

    /**
     * Regenerates a pair of access and refresh tokens using the provided refresh token.
     *
     * @param refreshToken The refresh token used for token regeneration.
     * @return LoggedUserDTO containing the new access token, refresh token, and user details.
     */
    @Override
    public LoggedUserDTO regeneratePairOfTokens(@Valid @NotBlank(message = Constants.EMPTY_TOKEN_MESSAGE)
                                                String refreshToken) {
        if (!jwtProvider.validateRefreshToken(refreshToken)) {
            throw new InvalidRefreshTokenException();
        }
        String email = jwtProvider.getRefreshClaims(refreshToken).getSubject();
        UserDTO user = userService.findByEmail(email);
        return generatePairOfTokens(user);
    }

}