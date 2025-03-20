package by.lms.libraryms.conf.auth;

import by.lms.libraryms.dto.common.UserDTO;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static by.lms.libraryms.utils.Constants.ID_CLAIM;
import static by.lms.libraryms.utils.Constants.ROLE_CLAIM;

//TODO сделать логгер
@Component
public class JwtProvider {
    @Value("spring.application.security.jwt.access-key.secret")
    private String jwtAccessSecret;
    @Value("${spring.application.security.jwt.refresh-key.secret}")
    private String jwtRefreshSecret;
    @Value("${spring.application.security.jwt.access-key.expiration-time}")
    private Integer jwtAccessExpirationTime;
    @Value(("${spring.application.security.jwt.refresh-key.expiration-time}"))
    private Integer jwtRefreshExpirationTime;

    /**
     * Generates JWT access token.
     *
     * @param userDTO represents a user data transfer object.
     * @return String representation of JWT access token.
     * @see UserDTO
     */
    public String generateAccessToken(@NotNull UserDTO userDTO) {
        final Date accessTokenExpiration = generateAccessTokenExpiration(jwtAccessExpirationTime);
        return Jwts.builder()
                .subject(userDTO.getUsername())
                .expiration(accessTokenExpiration)
                .claim(ID_CLAIM, userDTO.getId())
                .claim(ROLE_CLAIM, userDTO.getRoleIds())
                .signWith(getJwtAccessSecret(), Jwts.SIG.HS256)
                .compact();
    }

    /**
     * Generates JWT refresh token.
     *
     * @param userDTO represents a user data transfer object.
     * @return String representation of JWT refresh token.
     * @see UserDTO
     */
    public String generateRefreshToken(@NotNull UserDTO userDTO) {
        final Date refreshExpiration = generateAccessTokenExpiration(jwtRefreshExpirationTime);
        return Jwts.builder()
                .subject(userDTO.getUsername())
                .expiration(refreshExpiration)
                .signWith(getJwtRefreshSecret())
                .compact();
    }

    /**
     * Validates JWT access token.This method calls {@link JwtProvider#validateToken(String, SecretKey)}.
     *
     * @param accessToken String representation of JWT access token.
     * @return true if the JWT access token is valid, false otherwise.
     */
    public boolean validateAccessToken(@NotNull String accessToken) {
        return validateToken(accessToken, getJwtAccessSecret());
    }
    /**
     * Validates JWT refresh token.This method calls {@link JwtProvider#validateToken(String, SecretKey)}.
     *
     * @param refreshToken String representation of JWT refresh token.
     * @return true if the JWT refresh token is valid, false otherwise.
     */
    public boolean validateRefreshToken(@NotNull String refreshToken) {
        return validateToken(refreshToken, getJwtRefreshSecret());
    }

    /**
     * Validates a JWT token using the provided secret key.
     *
     * @param token     String representation of JWT token to validate.
     * @param secretKey The secret key used to sign the JWT token.
     * @return true if the token is valid, false otherwise.
     */
    private boolean validateToken(@NotNull String token, @NotNull SecretKey secretKey) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token: {}" + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.out.println("JWT token has expired: {}"+ e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("Unsupported JWT token format: {}"+ e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is null or empty or only whitespace: {}"+ e.getMessage());
        }

        return false;
    }

    /**
     * Extracts claim from JWT access token.
     *
     * @param token String representation of JWT access token.
     * @return The claims contained in the JWT access token.
     */
    public Claims getAccessClaims(@NotNull String token) {
        return getClaims(token, getJwtAccessSecret());
    }

    /**
     * Extracts claim from JWT refresh token.
     *
     * @param token String representation of JWT refresh token.
     * @return The claims contained in the JWT refresh token.
     */
    public Claims getRefreshClaims(@NotNull String token) {
        return getClaims(token, getJwtRefreshSecret());
    }

    /**
     * Retrieves the secret key used for creating JWT access tokens.
     *
     * @return The secret key for JWT access tokens.
     */
    private SecretKey getJwtAccessSecret() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessSecret));
    }

    /**
     * Retrieves the secret key used for creating JWT refresh tokens.
     *
     * @return The secret key for JWT refresh tokens.
     */
    private SecretKey getJwtRefreshSecret() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtRefreshSecret));
    }

    /**
     * Parses the given JWT token using the provided secret key and returns the claims from the token.
     *
     * @param token     The JWT token to be parsed.
     * @param secretKey The secret key used for parsing the JWT token.
     * @return Claims contained in the JWT token.
     * @throws NullPointerException if token or secretKey is null.
     */
    private Claims getClaims(@NotNull String token, @NotNull SecretKey secretKey) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Generates an expiration date for access based on the current date/time and the specified expiration time in minutes.
     *
     * @param expirationTime The expiration time in minutes.
     * @return Date The expiration date for access.
     */
    private Date generateAccessTokenExpiration(@NotNull Integer expirationTime) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant accessExpirationInstant = now.plusMinutes(expirationTime)
                .atZone(ZoneId.systemDefault())
                .toInstant();
        return Date.from(accessExpirationInstant);
    }


}
