package by.lms.libraryms.services.impl;

import by.lms.libraryms.domain.auth.User;
import by.lms.libraryms.dto.common.UserDTO;
import by.lms.libraryms.dto.req.UserLoginDTO;
import by.lms.libraryms.dto.resp.LoggedUserDTO;
import by.lms.libraryms.mappers.UserMapper;
import by.lms.libraryms.services.AuthService;
import by.lms.libraryms.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of AuthService interface.
 *
 * @see AuthService
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRED)
public class AuthServiceImpl implements AuthService {
    /**
     * UserDetailsService bean.
     *
     * @see UserDetailsServiceImpl
     */
    private final UserDetailsService userDetailsService;
    /**
     * AuthenticationManager bean.
     *
     * @see by.lms.libraryms.conf.auth.WebSecurityConfig
     */
    private final AuthenticationManager authenticationManager;
    /**
     * JwtService bean.
     *
     * @see JwtService
     */
    private final JwtService jwtService;
    private final UserMapper userMapper;

    /**
     * Authenticates user with provided credentials and generates JWT tokens.
     *
     * @param req The UserLoginDTO containing user login credentials.
     * @return The LoggedUserDTO with JWT tokens.
     * @see LoggedUserDTO
     * @see JwtService
     */
    @Override
    public LoggedUserDTO loginUser(UserLoginDTO req) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                req.getEmail(),
                req.getPassword()
        ));

        User user = (User) userDetailsService.loadUserByUsername(req.getEmail());
        UserDTO userDTO = userMapper.toDTO(user);

        return jwtService.generatePairOfTokens(userDTO);
    }

    /**
     * Regenerates JWT tokens using a refresh token.
     *
     * @param refreshToken The refresh token for re-logging in the user.
     * @return The LoggedUserDTO with new JWT tokens.
     * @see LoggedUserDTO
     * @see JwtService
     */
    @Override
    public LoggedUserDTO reLoginUser(String refreshToken) {
        return jwtService.regeneratePairOfTokens(refreshToken);
    }
}