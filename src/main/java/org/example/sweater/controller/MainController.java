package org.example.sweater.controller;

import org.example.sweater.entity.Message;
import org.example.sweater.entity.User;
import org.example.sweater.repository.MessageRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
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

    @GetMapping("/main")
    public String main(@RequestParam(required = false, defaultValue = "") String tag, Model model) {
        Iterable<Message> messages;
        if (tag != null && !tag.isBlank()) {
            messages = messageRepo.findByTag(tag);
        } else {
            messages = messageRepo.findAll();
        }
        model.addAttribute("messages", messages);
        model.addAttribute("tag", tag);
        return "main";
    }

    /**
     * @AuthenticationPrincipal User user - получаем аутентифицированного юзера, кот-й делает запрос
     */
    @PostMapping("/main")
    public String add(@AuthenticationPrincipal User user,
                      @RequestParam String text,
                      @RequestParam String tag, Map<String, Object> model,
                      @RequestParam("file") MultipartFile file) throws IOException {  // получаем файл
        Message message = new Message(text, tag, user);
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

        messageRepo.save(message);

        /* добавили в модель текущие сообщения и снова вернули шаблон main*/
        Iterable<Message> messages = messageRepo.findAll();
        model.put("messages", messages);

        return "main";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String tag, Map<String, Object> model) {
        Iterable<Message> messages;
        if (tag != null && !tag.isBlank()) {
            messages = messageRepo.findByTag(tag);
        } else {
            messages = messageRepo.findAll();
        }

        model.put("messages", messages);
        return "main";
    }
}