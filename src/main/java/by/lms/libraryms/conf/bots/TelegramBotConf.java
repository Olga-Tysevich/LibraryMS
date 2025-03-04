package by.lms.libraryms.conf.bots;

import by.lms.libraryms.services.impl.TelegramBotService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class TelegramBotConf {
    @Value("${telegram.bots.libraryMsBot.name}")
    private String botName;
    @Value("${telegram.bots.libraryMsBot.token}")
    private String botToken;
    @Value("${telegram.bots.libraryMsBot.commonChannelId}")
    private String commonChannelId;

    @Bean
    public TelegramBotService telegramBot() {
        return new TelegramBotService(this);
    }
}
