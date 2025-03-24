package by.lms.libraryms.services;

import by.lms.libraryms.services.messages.Message;

public interface NotificationService<T> {
    void sendMessage(Message message);
    void createReport(ReportTypeEnum type, T object);
}
