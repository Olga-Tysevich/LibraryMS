package by.lms.libraryms.services.impl;

import by.lms.libraryms.domain.ConfirmationCode;
import by.lms.libraryms.repo.ConfirmationCodeRepo;
import by.lms.libraryms.services.ConfirmationCodeService;
import by.lms.libraryms.utils.ConfirmationCodeGenerator;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class ConfirmationCodeServiceImpl implements ConfirmationCodeService {
    @Value("spring.mail.code.length")
    private int codeLength;
    @Value("spring.mail.code.timeToLive")
    private int timeToLive;
    private final ConfirmationCodeRepo confirmationCodeRepo;

    @Override
    @Transactional
    public ConfirmationCode createConfirmationCode(String userId) {
        String code = ConfirmationCodeGenerator.generateCode(codeLength);
        ConfirmationCode result = ConfirmationCode.builder()
                .code(code)
                .userId(new ObjectId(userId))
                .expiresAt(Instant.now().plusSeconds(timeToLive))
                .build();
        confirmationCodeRepo.deleteAllByExpiresAtLessThan(Instant.now());
        return confirmationCodeRepo.save(result);
    }


}

