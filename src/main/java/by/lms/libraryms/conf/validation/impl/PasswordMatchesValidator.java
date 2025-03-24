package by.lms.libraryms.conf.validation.impl;

import by.lms.libraryms.conf.validation.PasswordMatches;
import by.lms.libraryms.dto.req.CreateUserDTO;
import by.lms.libraryms.exceptions.UnsupportedDTOException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.Objects;

/**
 * A validator class for checking if the password and password confirmation match in a user DTO object.
 */
public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    /**
     * Initializes the validator.
     *
     * @param constraintAnnotation annotation instance for a given constraint declaration
     */
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    /**
     * Validates if the password and password confirmation match in the user DTO object.
     *
     * @param obj     the user DTO object.
     * @param context context in which the constraint is evaluated.
     * @return true if the passwords match, false otherwise.
     * @throws by.lms.libraryms.exceptions.UnsupportedDTOException if the DTO object is not supported.
     */
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        if (obj instanceof CreateUserDTO userDTO) {
            if (Objects.nonNull(userDTO.getPassword())) {
                return Arrays.equals(userDTO.getPassword(), userDTO.getPasswordConfirmation());
            }
        }
        throw new UnsupportedDTOException();
    }
}