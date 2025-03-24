package by.lms.libraryms.utils;

import lombok.experimental.UtilityClass;

import java.security.SecureRandom;

@UtilityClass
public class ConfirmationCodeGenerator {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String generateCode(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder code = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            code.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }

        return code.toString();
    }
}
