package org.example.sweater.controller;

import org.example.sweater.entity.Message;
import org.example.sweater.entity.User;
import org.example.sweater.entity.dto.MessageDto;
import org.example.sweater.repository.MessageRepo;
import org.example.sweater.service.MessageService;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

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
public class MessageController {
    private final MessageRepo messageRepo;
    private final MessageService messageService;

    /* получаем переменную из пропертис */
    @Value("${upload.path}")
    private String uploadPath;

    public MessageController(MessageRepo messageRepo, MessageService messageService) {
        this.messageRepo = messageRepo;
        this.messageService = messageService;
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
                       @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                       @AuthenticationPrincipal  User currentUser) {

        Page<MessageDto> page = messageService.messageList(pageable, tag, currentUser);

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

    @GetMapping("/user-messages/{author}")
    public String userMessages(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User author, // юзер - на страницу сообщений, которого зашли
            Model model,
            @RequestParam(required = false) Message message,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        /* сообщения конкретного юзера */
        Page<MessageDto> page = messageService.messageListForUser(pageable, author, currentUser);

        model.addAttribute("userChannel", author);
        model.addAttribute("subscriptionsCount", author.getSubscriptions().size());
        model.addAttribute("subscribersCount", author.getSubscribers().size());
        model.addAttribute("isSubscriber", author.getSubscribers().contains(currentUser));
        model.addAttribute("isCurrentUser", currentUser.equals(author));
        model.addAttribute("messageFromDB", message);

        model.addAttribute("url", "/user-messages/" + author.getId());
        model.addAttribute("page", page);

        return "userMessages";
    }

    @PostMapping("/user-messages/{author}")
    public String updateMessage(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User author,
            @RequestParam(name = "id", required = false) Message message,
            @RequestParam String text,
            @RequestParam String tag,
            @RequestParam MultipartFile file
    ) throws IOException {
        if (!author.equals(currentUser) || StringUtils.isEmpty(text)) { // лучше добавить валидацию и вынести общее из add()
            return "redirect:/user-messages/" + author.getId();
        }

        if (message == null) {
            message = new Message();
            message.setAuthor(currentUser);
        }

        message.setText(text);
        message.setTag(tag);


        saveFile(message, file);

        messageRepo.save(message);


        return "redirect:/user-messages/" + author.getId();
    }

    @GetMapping("/messages/{message}/like")
    public String like(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Message message,
            /* позволяет передать атрибуты в редирект */
            RedirectAttributes redirectAttributes,
            /* получат хедер из http запроса (для получения страницы, с которой пришли) */
            @RequestHeader(required = false) String referer
    ) {
        Set<User> likes = message.getLikes();

        if (likes.contains(currentUser)) {
            likes.remove(currentUser);
        } else {
            likes.add(currentUser);
        }
        /* получаем ури компоненты из ссылки */
        UriComponents components = UriComponentsBuilder.fromHttpUrl(referer).build();

        /* получаем параметры запроса */
        components.getQueryParams()
                .entrySet()
                /* в redirectAttributes передаем атрибуты, которые получили в месте с запросом */
                .forEach(pair -> redirectAttributes.addAttribute(pair.getKey(), pair.getValue()));

        return "redirect:" + components.getPath();
    }
}