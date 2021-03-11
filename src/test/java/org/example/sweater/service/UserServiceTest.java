package org.example.sweater.service;

import org.example.sweater.entity.Role;
import org.example.sweater.entity.User;
import org.example.sweater.repository.UserRepo;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.mockito.Mockito.times;


@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepo userRepo;

    @MockBean
    private MailSender mailSender;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    void addUser() {
        User user = new User();

        user.setEmail("some@mail.ru");

        boolean isUserCreated = userService.addUser(user);

        Assert.assertTrue(isUserCreated);
        Assert.assertNotNull(user.getActivationCode());
        Assert.assertTrue(CoreMatchers.is(user.getRoles()).matches(Collections.singleton(Role.USER)));

        /* проверяем, что из мока репозитория был вызван метод save 1 раз */
        Mockito.verify(userRepo, times(1)).save(user);
        /* проверяем, что из мока mailSender был вызван метод send 1 раз */
        Mockito.verify(mailSender, Mockito.times(1))
                .send(
                        ArgumentMatchers.eq(user.getEmail()), // проверяем, что метод вызван с корректным емейлом
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString()
                );
    }

    /**
     * Когда Юзер уже существует
     */
    @Test
    void addUserFailTest() {
        User user = new User();

        user.setUsername("John");

        /* Spy для userRepo: при вызове userRepo.findByUsername("John") возвращает некоторый объект */
        Mockito.doReturn(new User())
                .when(userRepo)
                .findByUsername("John");

        boolean isUserCreated = userService.addUser(user);

        Assert.assertFalse(isUserCreated);

        /* проверяем, что методы не вызываются */
        Mockito.verify(userRepo, times(0)).save(ArgumentMatchers.any(User.class));
        Mockito.verify(mailSender, Mockito.times(0))
                .send(
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString()
                );
    }

    @Test
    void activateUser() {
        User user = new User();
        user.setActivationCode("bingo");
        Mockito.doReturn(user)
                .when(userRepo)
                .findByActivationCode("activate");

        boolean isUserActivate = userService.activateUser("activate");
        Assert.assertTrue(isUserActivate);
        Assert.assertNull(user.getActivationCode());
        Mockito.verify(userRepo, times(1)).save(user);
    }

    @Test
    public void activateUserFailTest() {
        boolean isUserActivated = userService.activateUser("activate me");

        Assert.assertFalse(isUserActivated);

        Mockito.verify(userRepo, Mockito.times(0)).save(ArgumentMatchers.any(User.class));
    }
}