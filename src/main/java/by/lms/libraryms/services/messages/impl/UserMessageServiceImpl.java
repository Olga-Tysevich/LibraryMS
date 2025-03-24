package by.lms.libraryms.services.messages.impl;

import by.lms.libraryms.conf.i18n.MessageConf;
import by.lms.libraryms.conf.i18n.MessageTypeEnum;
import by.lms.libraryms.dto.common.UserDTO;
import by.lms.libraryms.dto.resp.ObjectChangedDTO;
import by.lms.libraryms.services.messages.UserMessageService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMessageServiceImpl extends AbstractMessageServiceImpl<UserDTO> implements UserMessageService {

    public UserMessageServiceImpl(MessageConf messageConf) {
        super(messageConf);
    }

    @Override
    protected void addSpecific(MessageTypeEnum typeEnum, ObjectChangedDTO<UserDTO> dto, List<Object> args) {

    }

    @Override
    protected String getPattern(MessageTypeEnum typeEnum) {
        return "";
    }
}
