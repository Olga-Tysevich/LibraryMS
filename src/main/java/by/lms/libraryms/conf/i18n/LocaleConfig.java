package by.lms.libraryms.conf.i18n;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

//TODO ДОБАВИТЬ ПЕРЕХВАТЧИК ОШИБОК (ЧИТАЙ "ЛОКАЛИЗАЦИЯ") И РАЗООБРАТЬСЯ С ЛОКАЛИЗАЦИЕЙ
@Configuration
public class LocaleConfig implements WebMvcConfigurer {

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.ENGLISH);
        return localeResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Bean
    public Locale currentUserLocale(HttpServletRequest request) {
        // Получаем имя пользователя из сессии или запроса
        String username = "currentUser"; // Это можно получить из текущего контекста или сессии
//        User user = userService.getUserByUsername(username);  // Получаем пользователя из БД
//
//        String locale = user.getLocale() != null ? user.getLocale() : Locale.ENGLISH.getLanguage();
        return Locale.of("en", "US");
    }
}
