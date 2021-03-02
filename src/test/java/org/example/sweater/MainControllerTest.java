package org.example.sweater;

import org.example.sweater.controller.MainController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails(value = "admin")
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql", "/messages-list-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/messages-list-after.sql", "/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MainController controller;


    /**
     * Проверяем конкретный элемент на главной странице
     */
    @Test
    public void mainPageTest() throws Exception {
        mockMvc.perform(get("/main"))
                .andDo(print())
                /* проверяем, что юзер аутентифицирован (для юзера в контексте установлена сессия) */
                .andExpect(authenticated())
                /* ищем в пришедшем дереве по xpath элемент с атрибутом id и значением = navbarSupportedContent,
                 * который содержит строку "admin" */
                .andExpect(xpath("//*[@id=\"navbarSupportedContent\"]/div").string("admin"));
    }

    /**
     * Проверяем кол-во сообщений на главной странице
     */
    @Test
    public void messageListTest() throws Exception {
        mockMvc.perform(get("/main"))
                .andDo(print())
                .andExpect(authenticated())
                /* определяет кол-во найденных узлов (элементов), т.е. дложно быть 4 сообщения */
                .andExpect(xpath("//*[@id=\"message-list\"]/div").nodeCount(4));
    }

    /**
     * После фильтрации по тегу, получаем 2 элемента
     */
    @Test
    public void filterMessageTest() throws Exception {
        this.mockMvc.perform(get("/main").param("tag", "my-tag"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='message-list']/div").nodeCount(2))
                /* проверяем наличие элемента с конкретным id (к xpath добавили [@data-id='1']) */
                .andExpect(xpath("//*[@id='message-list']/div[@data-id='1']").exists())
                .andExpect(xpath("//*[@id='message-list']/div[@data-id='3']").exists());

    }


    /**
     * Проверка формы редактирования/сохранения сообщения
     */
    @Test
    public void addMessageToListTest() throws Exception {
        /* передаем кроме параметров - файл*/
        MockHttpServletRequestBuilder multipart = multipart("/main")
                .file("file", "123".getBytes())
                .param("text", "fifth")
                .param("tag", "new one")
                /* в форму нужно также передавать csrf токен */
                .with(csrf());

        this.mockMvc.perform(multipart)
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='message-list']/div").nodeCount(5))
                /* id=10, т.к. в скрипте messages-list-before.sql мы указали alter sequence hibernate_sequence restart with 10 */
                .andExpect(xpath("//*[@id='message-list']/div[@data-id='10']").exists())
                .andExpect(xpath("//*[@id='message-list']/div[@data-id='10']/div/span").string("fifth"))
                .andExpect(xpath("//*[@id='message-list']/div[@data-id='10']/div/i").string("new one"));
    }
}
