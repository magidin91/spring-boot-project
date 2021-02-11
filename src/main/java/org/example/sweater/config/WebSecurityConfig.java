package org.example.sweater.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import javax.sql.DataSource;

/**
 * При старте приложения конфигурирует веб-секьюрити, в метод configure передается объект HttpSecurity,
 * который настраивается последующими методами.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    final DataSource dataSource;

    public WebSecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/", "/registration").permitAll() // по этому пути разрешаем полный доступ
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
        auth.jdbcAuthentication()
                /* dataSource нужен, чтобы менеджер мог подключиться к бд и получить юзеров и их роли*/
                .dataSource(dataSource)
                /* шифрует пароли, чтобы они не хранились в явном виде */
                .passwordEncoder(NoOpPasswordEncoder.getInstance()) // для тестирования используем без шифрования
                /* запрос, чтобы система могла найти пользователя по его имени*/
                .usersByUsernameQuery("select username, password, active from usr where username=?")
                /* получаем роли по имени пользавтеля */
                .authoritiesByUsernameQuery("select u.username, ur.roles from usr u inner join user_role ur on u.id = ur.user_id where u.username=?");
    }
}