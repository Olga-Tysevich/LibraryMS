package by.lms.libraryms.conf.validation;


import by.lms.libraryms.dto.common.UserDTO;
import by.lms.libraryms.exceptions.UnsupportedDTOException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

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
        if (obj instanceof UserDTO userDTO) {
            if (Objects.nonNull(userDTO.getPassword())) {
                return userDTO.getPassword().equals(userDTO.getPasswordConfirmation());
            }
        }
        throw new UnsupportedDTOException();
    }
}