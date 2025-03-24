package by.lms.libraryms.services.impl.notification;

import by.lms.libraryms.services.NotificationService;
import by.lms.libraryms.services.ReportTypeEnum;
import by.lms.libraryms.services.messages.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Objects;

@Service("emailNotificationService")
@RequiredArgsConstructor
public class EmailNotificationService<T> implements NotificationService<T> {
    private final JavaMailSender mailSender;

    @Override
    public void sendMessage(Message message) {
        String subject = message.getSubject();
        String to = message.getTo();
        String text = message.getText();
        if (Objects.nonNull(subject) && Objects.nonNull(to) && Objects.nonNull(text)) {
            SimpleMailMessage result = new SimpleMailMessage();
            result.setTo(to);
            result.setSubject(subject);
            result.setText(text);
            mailSender.send(result);
        }
    }

    @Override
    public void createReport(ReportTypeEnum type, T object) {

    }
}
