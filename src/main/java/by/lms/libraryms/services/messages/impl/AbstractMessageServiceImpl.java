package by.lms.libraryms.services.messages.impl;

import by.lms.libraryms.conf.i18n.MessageConf;
import by.lms.libraryms.services.messages.MessageService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.text.MessageFormat;
import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public abstract class AbstractMessageServiceImpl<T> implements MessageService<T> {
    private final MessageConf messageConf;

    public String createMessage(String pattern, LocalDateTime dateTime, Object... args) {
        return dateTime + " " + MessageFormat.format(pattern, args);
    }

    protected MessageConf messageConf() {
        return messageConf;
    }

}
