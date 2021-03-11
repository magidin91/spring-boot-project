package org.example.sweater.controller;

import org.example.sweater.entity.Message;
import org.example.sweater.entity.User;
import org.example.sweater.repository.MessageRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * GreetingController by Mustache
 */
@Controller
public class MainController {
    final MessageRepo messageRepo;
    /* получаем переменную из пропертис */
    @Value("${upload.path}")
    private String uploadPath;

    public MainController(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

    /**
     * Страница с сообщениями всех юзеров
     */
    @GetMapping("/main")
    public String main(@RequestParam(required = false, defaultValue = "") String tag,
                       Model model,
            /* @PageableDefault - дефолтные параметры Pageable, если не получаем их с фронта */
                       @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Message> page;
        if (tag != null && !tag.isBlank()) {
            page = messageRepo.findByTag(tag, pageable);
        } else {
            page = messageRepo.findAll(pageable);
        }
        model.addAttribute("url", "/main");
        model.addAttribute("page", page);
        model.addAttribute("tag", tag);

        return "main";
    }

    /* @AuthenticationPrincipal User user - получаем аутентифицированного юзера, кот-й делает запрос,
     * из контекста, чтобы не обращаться к БД */
    /* сохранение сообщения */
    @PostMapping("/main")
    public String add(@AuthenticationPrincipal User user,
                      @Valid Message message,
            /* Список аргументов и сообщений ошибок валидаций */
                      BindingResult bindingResult,
                      Model model,
                      @RequestParam("file") MultipartFile file) throws IOException {  // получаем файл
        message.setAuthor(user);

        /* если есть ошибки валидации, выводим их в шаблоны */
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorMap); // добавляет все атрибуты из мапы в модель
            model.addAttribute("message", message);
        }

        /* если нет ошибок валидации, сохраняем сообщение в БД */
        else {
            saveFile(message, file);
            /* чтобы форма не оставалась развернутой при удачном добавлении сообщения */
            model.addAttribute("message", null);

            messageRepo.save(message);
        }

        /* добавили в модель текущие сообщения и снова вернули шаблон main*/
        Iterable<Message> messages = messageRepo.findAll();
        model.addAttribute("messages", messages);

        return "main";
    }

    private void saveFile(@Valid Message message, @RequestParam("file") MultipartFile file) throws IOException {
    /* !file.getOriginalFilename().isEmpty() - Проверяем, что у файла задано имя,
 отсекая случаи, когда добавляем сообщение без изобр-ия */
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            /* проверяем, существует ли директория для сохранения файлов, если нет - создаем */
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            /* генерируем UUID */
            String uuidFile = UUID.randomUUID().toString();
            /* получим подобное название: 125e488f-a64e-497d-8c53-9d2573e9f85a.приоритет */
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            /* создаем файл и передаем в него полученный MultipartFile file */
            file.transferTo(new File(uploadPath + "/" + resultFilename));
            message.setFilename(resultFilename);
        }
    }

    @GetMapping("/user-messages/{user}")
    public String userMessages(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user, // юзер - на страницу сообщений, которого зашли
            Model model,
            @RequestParam(required = false) Message message
    ) {
        /* сообщения конкретного юзера */
        Set<Message> messages = user.getMessages();
        model.addAttribute("userChannel", user);
        model.addAttribute("subscriptionsCount", user.getSubscriptions().size());
        model.addAttribute("subscribersCount", user.getSubscribers().size());
        model.addAttribute("isSubscriber", user.getSubscribers().contains(currentUser));
        model.addAttribute("messages", messages);
        model.addAttribute("isCurrentUser", currentUser.equals(user));
        model.addAttribute("messageFromDB", message);

        return "userMessages";
    }

    @PostMapping("/user-messages/{user}")
    public String updateMessage(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user,
            @RequestParam(name = "id", required = false) Message message,
            @RequestParam String text,
            @RequestParam String tag,
            @RequestParam MultipartFile file
    ) throws IOException {
        if (!user.equals(currentUser) || StringUtils.isEmpty(text)) { // лучше добавить валидацию и вынести общее из add()
            return "redirect:/user-messages/" + user.getId();
        }

        if (message == null) {
            message = new Message();
            message.setAuthor(currentUser);
        }

        message.setText(text);
        message.setTag(tag);


        saveFile(message, file);

        messageRepo.save(message);


        return "redirect:/user-messages/" + user.getId();
    }
}