package by.lms.libraryms.conf.validation.impl;
import by.lms.libraryms.conf.validation.ValidPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.nio.CharBuffer;
import java.util.Objects;
import java.util.regex.Pattern;

import static by.lms.libraryms.utils.Constants.PASSWORD_REGEX;

public class PasswordValidator implements ConstraintValidator<ValidPassword, char[]> {

    @Override
    public boolean isValid(char[] password, ConstraintValidatorContext context) {
        if (Objects.isNull(password) || password.length == 0) {
            return false;
        }
        return Pattern.matches(PASSWORD_REGEX, CharBuffer.wrap(password));
    }
}
