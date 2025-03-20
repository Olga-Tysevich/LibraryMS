package by.lms.libraryms.filters;

import by.lms.libraryms.conf.auth.JwtProvider;
import by.lms.libraryms.exceptions.UnauthorizedException;
import by.lms.libraryms.utils.Constants;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static by.lms.libraryms.utils.Constants.TOKEN_HEADER;
import static by.lms.libraryms.utils.Constants.TOKEN_TYPE;


/**
 * This class represents a JWT authentication filter that extends OncePerRequestFilter. *
 * It is responsible for authenticating JWT tokens and setting the authenticated user details in the SecurityContext.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    /**
     * JwtProvider bean.
     * @see JwtProvider
     */
    private final JwtProvider jwtProvider;
    /**
     * UserDetailsService bean.
     * @see UserDetailsServiceImpl
     */
    private final UserDetailsService userDetailsService;

    /**
     * This method filters incoming requests, checks for JWT tokens, validates them, and authenticates the user.
     *
     * @param request     the HTTP servlet request.
     * @param response    the HTTP servlet response.
     * @param filterChain the FilterChain.
     * @throws ServletException in case of any servlet related exceptions.
     * @throws IOException      in case of any IO related exceptions.
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {

            String header = request.getHeader(TOKEN_HEADER);
            if (StringUtils.isBlank(header) || !StringUtils.startsWith(header, TOKEN_TYPE)) {
                filterChain.doFilter(request, response);
                return;
            }

            String jwt = header.substring(TOKEN_TYPE.length());
            if (!jwtProvider.validateAccessToken(jwt)) {
                throw new UnauthorizedException();
            }

            String email = jwtProvider.getAccessClaims(jwt).getSubject();
            if (StringUtils.isNotBlank(email) && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                context.setAuthentication(authToken);
                SecurityContextHolder.setContext(context);
            } else {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                if (!auth.getPrincipal().equals(email)) {
                    throw new JwtException(Constants.TOKEN_WAS_STOLEN_MESSAGE);
                }
            }

            filterChain.doFilter(request, response);

        } catch (UnauthorizedException | JwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().print(e.getMessage());
        }
    }

    /**
     * This method determines whether the filter should be applied to the request.
     * It excludes the filter for specific URI paths.
     * @param request the HTTP servlet request.
     * @return true if the filter should not be applied, false otherwise.
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getRequestURI().contains("api/v1/library/auth");
    }
}