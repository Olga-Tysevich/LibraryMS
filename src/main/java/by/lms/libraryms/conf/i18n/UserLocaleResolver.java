package by.lms.libraryms.conf.i18n;

import by.lms.libraryms.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class UserLocaleResolver implements LocaleResolver {
    private final UserService userService;

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (Objects.nonNull(auth) && auth.isAuthenticated() ) {
            String username = auth.getName();
            return userService.getCurrentLocale(username);
        }

        return Locale.ENGLISH;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {

    }
}
