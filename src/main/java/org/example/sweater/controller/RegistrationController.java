package org.example.sweater.controller;

import org.example.sweater.entity.User;
import org.example.sweater.entity.dto.CaptchaResponseDto;
import org.example.sweater.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {
    /* передаем secret и response параметрами */
    private final static String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";
    private final UserService userService;
    private final RestTemplate restTemplate;

    @Value("${recaptcha.secret}")
    private String secret;


    public RegistrationController(UserService userService, RestTemplate restTemplate) {
        this.userService = userService;
        this.restTemplate = restTemplate;
    }


    @GetMapping("registration")
    public String registration() {
        return "registration";
    }

    /**
     * Добавялет юзера при регистрации
     */
    @PostMapping("registration")
    public String addUser(
            @RequestParam("password2") String passwordConfirm,
            @RequestParam("g-recaptcha-response") String captchaResponse, // получаем рекапчу из формы регистрации
            @Valid User user,
            BindingResult bindingResult,
            Model model) {
        /* полный урл для запроса на сервер капчм */
        String url = String.format(CAPTCHA_URL, secret, captchaResponse);
        /* постзапрос по адресу урл, с пустым телом, в ответ получаем CaptchaResponseDto.class */
        CaptchaResponseDto response = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDto.class);

        /* проверяем прошла ли каптча */
        boolean isCaptchaSuccess = !response.isSuccess();
        if (isCaptchaSuccess) {
            model.addAttribute("captchaError", "Fill captcha");
        }

        boolean isConfirmEmpty = StringUtils.isEmpty(passwordConfirm);
        if (isConfirmEmpty) {
            model.addAttribute("password2Error", "Password confirmation cannot be empty");
        }

        boolean arePasswordsDifferent = user.getPassword() != null && !user.getPassword().equals(passwordConfirm);
        if (arePasswordsDifferent) {
            model.addAttribute("passwordError", "Passwords are different");
        }

        boolean hasValidErrors = bindingResult.hasErrors();
        if (hasValidErrors) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.addAllAttributes(errors);
        }

        if (isConfirmEmpty || arePasswordsDifferent || hasValidErrors || isCaptchaSuccess) {
            return "registration";
        }

        /* userService.addUser(user) - сохраняем пользователя */
        if (!userService.addUser(user)) {
            model.addAttribute("usernameError", "User exists!");
            return "registration";
        }

        return "redirect:/login";
    }

    /**
     * Активации пользователя после его перехода по ссылке в письме
     */
    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);

        if (isActivated) {
            /* успешная активация */
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "User successfully activated");
        } else {
            /* неуспешная активация */
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Activation code is not found!");
        }

        return "login";
    }
}
