package by.lms.libraryms.conf.auth;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;


@Configuration
public class AESKeyLoader {
    @Value("aes.key.path")
    private String aesKeyPath;
    private SecretKey secretKey;

    @PostConstruct
    public void init() throws IOException {
        this.secretKey = loadKeyFromFile(aesKeyPath);
    }

    private SecretKey loadKeyFromFile(String aesKeyPath) throws IOException{
        byte[] keyBytes = Files.readAllBytes(Paths.get(aesKeyPath));
        byte[] decodedKey = Base64.getDecoder().decode(keyBytes);
        return new SecretKeySpec(decodedKey, "AES");
    }

    @Bean
    public SecretKey getSecretKey() {
        return secretKey;
    }


}
