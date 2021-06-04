package ru.gontarenko.webquizengine.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(QuizController.class)
public class QuizControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getQuiz() throws Exception { // GET http://localhost:8090/api/quiz
        String url = "/api/quiz";
        MvcResult mvcResult = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();
        assertEquals(
                "{\"title\":\"The Java Logo\"," +
                        "\"text\":\"What is depicted on the Java logo?\"," +
                        "\"options\":[\"Robot\",\"Tea leaf\",\"Cup of coffee\",\"Bug\"]}",
                mvcResult.getResponse().getContentAsString()
        );
    }

    @Test
    void postQuizAnswerTwoShouldReturnTrue() throws Exception {
        String url = "/api/quiz";
        MvcResult mvcResult = mockMvc.perform(
                post(url).param("answer", "2")
        ).andExpect(status().isOk()).andReturn();
        assertEquals(
                "{\"success\":true,\"feedback\":\"Congratulations, you're right!\"}",
                mvcResult.getResponse().getContentAsString()
        );
    }

    @Test
    void postQuizAnswerNotTwoShouldReturnFalse() throws Exception {
        String url = "/api/quiz";
        MvcResult mvcResult = mockMvc.perform(
                post(url).param("answer", "1")
        ).andExpect(status().isOk()).andReturn();
        assertEquals(
                "{\"success\":false,\"feedback\":\"Wrong answer! Please, try again.\"}",
                mvcResult.getResponse().getContentAsString()
        );
    }
}
