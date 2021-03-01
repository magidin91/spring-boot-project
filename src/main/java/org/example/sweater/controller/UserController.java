package org.example.sweater.controller;

import org.example.sweater.entity.Role;
import org.example.sweater.entity.User;
import org.example.sweater.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Path;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Список юзеров
     */
    @PreAuthorize("hasAuthority('ADMIN')") /* класс User имеет метод getAuthorities(), т.к. Roles implements GrantedAuthority */
    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userService.findAll());
        return "userList";
    }

    /**
     * @PathVariable User user спринг получает юзера из ДБ по айди
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    /* @RequestParam("userId") User user - по userId будет получен юзер из БД
     * @RequestParam Map<String, String> form - мапа значений из формы имя + значение (админ - админ) */
    public String userSave(
            @RequestParam String username,
            @RequestParam Map<String, String> form,
            @RequestParam("userId") User user) {

        userService.saveUser(username, form, user);
        return "redirect:/user";
    }

    /* @AuthenticationPrincipal User user - получаем текущего аутентифицированного пользователя
    из контекста спринг секьюрити, чтобы не обращатсья к БД */
    @GetMapping("/profile")
    public String getProfile(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());

        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(
            @AuthenticationPrincipal User user,
            @RequestParam String password,
            @RequestParam String email) {

        userService.updateProfile(user, password, email);

        return "redirect:/user/profile";
    }

    @GetMapping("/subscribe/{user}")
    public String subscribe(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user) {

        userService.subscribe(currentUser, user);
        return "redirect:/user-messages/" + user.getId();
    }

    @GetMapping("/unsubscribe/{user}")
    public String unsubscribe(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user) {

        userService.unsubscribe(currentUser, user);
        return "redirect:/user-messages/" + user.getId();
    }

    /* выводит список подписчиков или подписок в зависимости от {type} в пути */
    @GetMapping("/{type}/{user}/list")
    public String userList(
            Model model,
            @PathVariable User user,
            @PathVariable String type
    ) {
        model.addAttribute("userChannel", user);
        model.addAttribute("type", type);
        if ("subscriptions".equals(type)) {
            model.addAttribute("users", user.getSubscriptions());
        } else {
            model.addAttribute("users", user.getSubscribers());
        }

        return "subscriptions";
    }
}
