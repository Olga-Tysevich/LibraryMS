package by.lms.libraryms.services.impl;

import by.lms.libraryms.services.ReportService;
import by.lms.libraryms.services.ReportTypeEnum;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl<T> implements ReportService<T> {
    @Override
    public void createReport(ReportTypeEnum type, T object) {
        System.out.println("Report type: " + type + " object: " + object);
    }
}
