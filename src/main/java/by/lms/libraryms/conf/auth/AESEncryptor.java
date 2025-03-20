package by.lms.libraryms.conf.auth;

import by.lms.libraryms.exceptions.AESEncryptorException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class AESEncryptor {
    private final SecretKey secretKey;

    public String encrypt(String input) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(input.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);

        } catch (NoSuchPaddingException
                 | IllegalBlockSizeException
                 | NoSuchAlgorithmException
                 | BadPaddingException
                 | InvalidKeyException e) {
            //TODO лог
            System.out.println(e.getMessage());
            throw new AESEncryptorException(e.getMessage());
        }
    }

    public String decrypt(String encryptedData) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = Base64.getDecoder().decode(encryptedData);
            return new String(decryptedBytes);
        } catch (NoSuchPaddingException
                 | NoSuchAlgorithmException
                 | InvalidKeyException e) {
            //TODO лог
            System.out.println(e.getMessage());
            throw new AESEncryptorException(e.getMessage());
        }
    }
}
