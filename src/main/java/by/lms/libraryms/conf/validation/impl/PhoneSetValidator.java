package by.lms.libraryms.conf.validation.impl;

import by.lms.libraryms.conf.validation.ValidPhoneSet;
import by.lms.libraryms.utils.Constants;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;
import java.util.Set;

public class PhoneSetValidator implements ConstraintValidator<ValidPhoneSet, Set<String>> {

    @Override
    public boolean isValid(Set<String> phones, ConstraintValidatorContext constraintValidatorContext) {

        if (Objects.isNull(phones) || phones.isEmpty()) {
            return false;
        }

        for (String phone : phones) {
            if (!phone.matches(Constants.PHONE_REGEX)) {
                return false;
            }
        }
        return true;
    }
}
