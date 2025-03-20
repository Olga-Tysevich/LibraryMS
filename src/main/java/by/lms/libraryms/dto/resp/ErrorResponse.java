package by.lms.libraryms.dto.resp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents an ErrorResponse object containing error details.
 * @see ForbiddenEntryPoint
 * @see TrackerAccessDeniedHandler
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private String error;
    private String message;
}