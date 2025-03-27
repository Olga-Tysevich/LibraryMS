package by.lms.libraryms.conf.auth;


import by.lms.libraryms.domain.RoleEnum;
import by.lms.libraryms.filters.JwtAuthFilter;
import by.lms.libraryms.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

/**
 * Configuration class for web security.
 */
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {
    /**
     * Authorization filter bean.
     *
     * @see JwtAuthFilter
     */
    private final JwtAuthFilter authFilter;
    /**
     * User loader bean.
     *
     * @see by.lms.libraryms.services.impl.UserDetailsServiceImpl
     */
    private final UserDetailsService userDetailsService;
    @Value("spring.application.security.admin.urls")
    private String[] adminUrls;
    @Value("spring.application.security.user.urls")
    private String[] userUrls;
    @Value("spring.application.security.reader.urls")
    private String[] readerUrls;
    @Value("spring.application.security.librarian.urls")
    private String[] librarianUrls;

    /**
     * Configures the security filter chain for HttpSecurity.
     *
     * @param httpSecurity The HttpSecurity object.
     * @return SecurityFilterChain object.
     * @throws Exception if an error occurs.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(request -> {
                    var corsConfig = new CorsConfiguration();
                    corsConfig.setAllowedOriginPatterns(List.of("*"));
                    corsConfig.setAllowedMethods(List.of("GET", "POST", "DELETE"));
                    corsConfig.setAllowedHeaders(List.of("*"));
                    corsConfig.setAllowCredentials(true);
                    return corsConfig;
                }))
                .authorizeHttpRequests(request -> request
                        .requestMatchers(Constants.IGNORE_URLS).permitAll()
                        .requestMatchers(adminUrls).hasRole(RoleEnum.ROLE_ADMIN.getRoleName())
                        .requestMatchers(userUrls).hasRole(RoleEnum.ROLE_USER.getRoleName())
                        .requestMatchers(readerUrls).hasRole(RoleEnum.ROLE_READER.getRoleName())
                        .requestMatchers(librarianUrls).hasRole(RoleEnum.ROLE_LIBRARIAN.getRoleName())
                        .anyRequest().permitAll())
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exception -> exception.accessDeniedHandler(accessDeniedHandler())
                        .authenticationEntryPoint(entryPoint()));
        return httpSecurity.build();
    }

    /**
     * Creates and configures the AuthenticationProvider.
     *
     * @return AuthenticationProvider object.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * Creates and configures the AuthenticationManager.
     *
     * @param config the AuthenticationConfiguration object.
     * @return AuthenticationManager object.
     * @throws Exception if an error occurs.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Creates and configures the PasswordEncoder.
     *
     * @return PasswordEncoder object.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Creates a ApplicationAccessDeniedHandler bean.
     *
     * @return ApplicationAccessDeniedHandler object.
     */
    @Bean
    public ApplicationAccessDeniedHandler accessDeniedHandler() {
        return new ApplicationAccessDeniedHandler();
    }

    /**
     * Creates a ForbiddenEntryPoint bean.
     *
     * @return ForbiddenEntryPoint object.
     */
    @Bean
    public ForbiddenEntryPoint entryPoint() {
        return new ForbiddenEntryPoint();
    }

}
