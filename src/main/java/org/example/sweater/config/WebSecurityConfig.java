package org.example.sweater.config;

import org.example.sweater.service.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * При старте приложения конфигурирует веб-секьюрити, в метод configure передается объект HttpSecurity,
 * который настраивается последующими методами.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import(BeanConfig.class)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    public WebSecurityConfig(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/", "/registration", "/static/**", "/activate/*").permitAll() // по этому пути разрешаем полный доступ
                    .anyRequest().authenticated() // для остальных запросов требуется авторизация
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .permitAll() // разрешаем логином пользоватсья всем
                .and()
                /* даже если сессия в сервлет контейнере истекла, спринг будет искать по полученным от вас идентификаторам
                * ваши настройки и постарается вас аутентифицировать повторно */
                    .rememberMe()
                .and()
                    .logout()
                    .permitAll(); // разрешаем логаутом пользоваться всем
    }

    /**
     * Конфигурирует менеджер аутентификации
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /* UserService реализует интерфейс UserDetailsService с методом loadUserByUsername, т.о.
         обеспечивает получение юзера (UserDetails) по юзернейму из БД */
        auth.userDetailsService(userService)
                /* PasswordEncoder хеширует пароли, также сравнивает введенный пароль и хешированный пароль */
                .passwordEncoder(passwordEncoder); // используем BCryptPasswordEncoder
    }
}