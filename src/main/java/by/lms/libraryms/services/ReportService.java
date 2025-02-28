package by.lms.libraryms.services;

public interface ReportService<T> {
    void createReport(ReportTypeEnum type, T object);
}
