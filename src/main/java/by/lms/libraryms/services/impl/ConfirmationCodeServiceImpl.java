package by.lms.libraryms.services.impl;

import by.lms.libraryms.domain.ConfirmationCode;
import by.lms.libraryms.services.ConfirmationCodeService;
import by.lms.libraryms.utils.ConfirmationCodeGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ConfirmationCodeServiceImpl implements ConfirmationCodeService {
    @Value("spring.mail.code.length")
    private int codeLength;

    @Override
    public ConfirmationCode createConfirmationCode(String userId) {
        String code = ConfirmationCodeGenerator.generateCode(codeLength);
        return null;
    }
}

