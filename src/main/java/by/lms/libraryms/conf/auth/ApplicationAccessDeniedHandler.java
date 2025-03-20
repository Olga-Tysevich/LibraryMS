package by.lms.libraryms.conf.auth;
import by.lms.libraryms.dto.resp.ErrorResponse;
import by.lms.libraryms.utils.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.io.OutputStream;
/**
 * AccessDeniedHandler implementation for handling access denied errors in the tracker application.
 */
public class ApplicationAccessDeniedHandler implements AccessDeniedHandler {
    /**
     * Handle the access denied exception by setting the response status to SC_FORBIDDEN (403),
     * setting the content type to APPLICATION_JSON_VALUE, and writing an ErrorResponse JSON object
     * with the appropriate message to the response output stream.
     *
     * @param request               that resulted in an <code>AccessDeniedException</code>.
     * @param response              so that the user agent can be advised of the failure.
     * @param accessDeniedException that caused the invocation.
     * @throws IOException if an I/O error occurs while writing to the output stream.
     */
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        try (OutputStream os = response.getOutputStream()) {
            ErrorResponse resp = new ErrorResponse(Constants.FORBIDDEN_KEY, Constants.FORBIDDEN_MESSAGE);
            new ObjectMapper().writeValue(os, resp);
            os.flush();
        }
    }
}
