package by.lms.libraryms.conf.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneSetValidator.class)
@Documented
public @interface ValidPhoneSet {
    String message() default "Invalid phone number format in the set";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
