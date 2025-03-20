package by.lms.libraryms.conf.auth;

import by.lms.libraryms.dto.resp.ErrorResponse;
import by.lms.libraryms.utils.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.io.OutputStream;



/**
 * This class represents an implementation of an AuthenticationEntryPoint that handles the commencement
 * of authentication when access to a resource is forbidden.
 */
public class ForbiddenEntryPoint implements AuthenticationEntryPoint {
    /**
     * This method is called when access to a resource is denied because the user is not authorized..It sets the HTTP response status to
     * SC_UNAUTHORIZED and writes an ErrorResponse object as a JSON value to the response output stream.
     *
     * @param request       the HttpServletRequest object representing the request made to the server.
     * @param response      the HttpServletResponse object representing the response to be sent back to the client.
     * @param authException the AuthenticationException object representing the authentication exception that occurred.
     * @throws IOException if an input or output exception occurs while writing to the response output stream.
     */
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        try (OutputStream os = response.getOutputStream()) {
            ErrorResponse resp = new ErrorResponse(Constants.FORBIDDEN_KEY, Constants.FORBIDDEN_MESSAGE);
            new ObjectMapper().writeValue(os, resp);
            os.flush();
        }
    }
}