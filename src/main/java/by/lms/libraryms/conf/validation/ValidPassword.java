package by.lms.libraryms.conf.validation;

import by.lms.libraryms.conf.validation.impl.PasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {
    String message() default "Invalid password format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
