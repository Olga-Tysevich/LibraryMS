package by.lms.libraryms;

import by.lms.libraryms.utils.DotenvLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LibraryMsApplication {

	public static void main(String[] args) {
		DotenvLoader.load();
		SpringApplication.run(LibraryMsApplication.class, args);
	}

	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasenames("by/lms/libraryms/i18n");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}
}
