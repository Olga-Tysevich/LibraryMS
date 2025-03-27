package by.lms.libraryms.services.messages.impl;

import by.lms.libraryms.conf.i18n.MessageConf;
import by.lms.libraryms.domain.ConfirmationCode;
import by.lms.libraryms.services.messages.ConfirmationMessageService;
import by.lms.libraryms.services.messages.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfirmationMessageServiceImpl implements ConfirmationMessageService {
    private final MessageConf messageConf;
    @Value("spring.mail.code.confirmation.url")
    private String emailConfirmationUrl;

    @Override
    public Message createEmailConfirmationMessage(String email, ConfirmationCode confirmationCode) {
        return Message.builder()
                .to(email)
                .subject(messageConf.getEmailConfirmationSubject())
                .text(messageConf.getEmailConfirmation() + "\n" + emailConfirmationUrl + confirmationCode.getCode())
                .build();
    }
}
