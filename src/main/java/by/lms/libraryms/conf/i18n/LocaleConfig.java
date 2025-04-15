package by.lms.libraryms.conf.i18n;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.util.Arrays;
import java.util.List;

@Configuration
public class LocaleConfig implements WebMvcConfigurer {
    public static final List<String> LOCALES = Arrays.asList("en", "ru");

    @Bean
    public LocaleResolver localeResolver(UserLocaleResolver userLocaleResolver) {
        return userLocaleResolver;
    }

    @Override
    public void addInterceptors(@NotNull InterceptorRegistry registry) {
    }
}
