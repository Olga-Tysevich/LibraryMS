package by.lms.libraryms.services.messages.impl;

import by.lms.libraryms.conf.i18n.MessageConf;
import by.lms.libraryms.conf.i18n.MessageTypeEnum;
import by.lms.libraryms.dto.resp.ObjectChangedDTO;
import by.lms.libraryms.services.messages.MessageService;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@RequiredArgsConstructor
public abstract class AbstractMessageServiceImpl<T > implements MessageService<T> {
    private final MessageConf messageConf;

    public String createMessage(@NotNull String pattern, @NotNull LocalDateTime dateTime, @NotNull Object... args) {
        return dateTime + " " + MessageFormat.format(pattern, args);
    }


    public String createMessage(@NotNull MessageTypeEnum typeEnum, @NotNull ObjectChangedDTO<T> dto) {
        String pattern = getPattern(typeEnum);
        T object = dto.getObject();
        List<Object> args = new ArrayList<>();
        if (Objects.nonNull(object)) {
            //TODO добавить библиотекаря
            args.add("");
            addSpecific(typeEnum, dto, args);
        }
        return createMessage(pattern, dto.getUpdatedAt(), args.toArray());
    }

    protected MessageConf messageConf() {
        return messageConf;
    }

    protected abstract void addSpecific(@NotNull MessageTypeEnum typeEnum,
                                        @NotNull ObjectChangedDTO<T> dto,
                                        @NotNull List<Object> args);

    protected abstract @NotNull String getPattern(@NotNull MessageTypeEnum typeEnum);


}
