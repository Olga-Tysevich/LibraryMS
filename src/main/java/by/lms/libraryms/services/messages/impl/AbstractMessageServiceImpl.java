package by.lms.libraryms.services.messages.impl;

import by.lms.libraryms.conf.i18n.MessageConf;
import by.lms.libraryms.conf.i18n.MessageTypeEnum;
import by.lms.libraryms.dto.resp.ObjectChangedDTO;
import by.lms.libraryms.dto.resp.ObjectListChangedDTO;
import by.lms.libraryms.services.messages.Message;
import by.lms.libraryms.services.messages.MessageService;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public abstract class AbstractMessageServiceImpl<T> implements MessageService<T> {
    private final MessageConf messageConf;

    public Message createMessage(@NotNull String pattern, @NotNull LocalDateTime dateTime, @NotNull Object... args) {
        return Message.builder()
                .text(dateTime + " " + MessageFormat.format(pattern, args))
                .build();
    }


    @Override
    public List<Message> createMessages(MessageTypeEnum typeEnum, ObjectListChangedDTO<T> objects) {
        List<ObjectChangedDTO<T>> objectList = objects.getObjects();
        if (Objects.nonNull(objectList)) {
            return objectList.stream()
                    .map(o -> createMessage(typeEnum, o))
                    .collect(Collectors.toList());
        }
        return List.of();
    }

    public Message createMessage(@NotNull MessageTypeEnum typeEnum, @NotNull ObjectChangedDTO<T> dto) {
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
