package by.lms.libraryms.services;

public interface NotificationService<T> {
    void sendMessage(String message);
    void createReport(ReportTypeEnum type, T object);

}
