package by.lms.libraryms.services.impl;

import by.lms.libraryms.conf.bots.TelegramBotConf;
import by.lms.libraryms.conf.bots.TelegramGroupMessageConfig;
import by.lms.libraryms.services.NotificationService;
import by.lms.libraryms.services.ReportTypeEnum;
import by.lms.libraryms.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class TelegramNotificationService<T> implements NotificationService<T> {
    private final TelegramBotConf config;
    private final TelegramGroupMessageConfig groupMessageConfig;
    private final RestTemplate restTemplate = new RestTemplate();
    private final Class<T> clazz;

    @Override
    public void sendMessage(String message) {

        String url = String.format(Constants.TELEGRAM_PATH_TEMPLATE,
                config.getBotToken(), config.getCommonChannelId(), message);

        String messageThreadId = groupMessageConfig.getGroupIdForClass(clazz);

        if (!messageThreadId.isBlank()) {
            String urlForGroup = String.format(Constants.TELEGRAM_PATH_TEMPLATE,
                    config.getBotToken(), groupMessageConfig.getGroupId(), message) + "&message_thread_id=" + messageThreadId;

            restTemplate.getForObject(urlForGroup, String.class);
        }


        restTemplate.getForObject(url, String.class);
    }

    @Override
    public void createReport(ReportTypeEnum type, T object) {
        System.out.println("Report type: " + type + " object: " + object);
    }

}
