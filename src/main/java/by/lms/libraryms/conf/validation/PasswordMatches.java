package by.lms.libraryms.conf.validation;

import by.lms.libraryms.conf.validation.impl.PasswordMatchesValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchesValidator.class)
@Documented
public @interface PasswordMatches {
    /**
     * The error message to be displayed when the passwords do not match.
     *
     * @return String the error message.
     */
    String message() default "Password mismatch!";

    /**
     * Groups targeted by this constraint.
     *
     * @return Class[] the groups targeted by this constraint.
     */
    Class<?>[] groups() default {};

    /**
     * Payload type that can be associated with a given constraint declaration.
     *
     * @return Class[] the payload type.
     */

    Class<? extends Payload>[] payload() default {};
}