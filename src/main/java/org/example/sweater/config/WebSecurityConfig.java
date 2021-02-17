package org.example.sweater.config;

import org.example.sweater.service.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

/**
 * При старте приложения конфигурирует веб-секьюрити, в метод configure передается объект HttpSecurity,
 * который настраивается последующими методами.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserService userService;

    public WebSecurityConfig(UserService userService) {
        this.userService = userService;
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
                    .logout()
                    .permitAll(); // разрешаем логаутом пользоваться всем
    }

    /**
     * Конфигурирует менеджер аутентификации
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService) // конфигурирует способ получения пользователя из бд
                /* шифрует пароли, чтобы они не хранились в явном виде */
                .passwordEncoder(NoOpPasswordEncoder.getInstance()); // для тестирования используем без шифрования
    }
}