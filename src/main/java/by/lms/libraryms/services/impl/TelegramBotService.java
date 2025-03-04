package by.lms.libraryms.services.impl;

import by.lms.libraryms.conf.bots.TelegramBotConf;
import by.lms.libraryms.services.UserService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Getter
public class TelegramBotService extends TelegramLongPollingBot {
//    @Autowired
//    private UserService userService;
    private final TelegramBotConf conf;

    public TelegramBotService(TelegramBotConf conf) {
        super(conf.getBotToken());
        this.conf = conf;
    }

    //TODO доделать позже отправку пользователям
    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();


            switch (message) {
                case "/start" -> registerUser();
            }

        }
    }

    @Override
    public String getBotUsername() {
        return conf.getBotName();
    }

    private void registerUser() {}

}
