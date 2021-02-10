package org.example.sweater.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * При старте приложения конфигурирует веб-секьюрити, в метод configure передается объект HttpSecurity,
 * который настраивается последующими методами.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/").permitAll() // по этому пути разрешаем полный доступ
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
     * Создает менеджер, который обслуживает учетные записи
     */
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("password")
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(user); // при каждом запуске создает пользователя "user" + "password"
    }
}