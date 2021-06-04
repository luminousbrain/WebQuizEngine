package ru.gontarenko.webquizengine.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.gontarenko.webquizengine.entities.Quiz;
import ru.gontarenko.webquizengine.services.QuizService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

// TODO: 04.06.2021 REFACTOR
@WebMvcTest(QuizController.class)
public class QuizControllerTest {
    private final String quizzesUrl = "/api/quizzes";
    private final String solveQuizById = "/api/quizzes/1/solve";

    private final String successSolve = "{\"success\":true,\"feedback\":\"Congratulations, you're right!\"}";
    private final String failSolve = "{\"success\":false,\"feedback\":\"Wrong answer! Please, try again.\"}";

    private final ObjectMapper mapper = new ObjectMapper();

    private static Quiz testQuiz1;
    private static Quiz testQuiz2;

    @BeforeAll
    public static void createTestQuizzes() {
        testQuiz1 = new Quiz("The Java Logo", "What is depicted on the Java logo?", List.of("Robot", "Tea leaf", "Cup of coffee", "Bug"), 2);
        testQuiz1.setId(1);
        testQuiz2 = new Quiz("Java older than C#", "Java older than C#?", List.of("yes", "no"), 0);
        testQuiz2.setId(2);
    }

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuizService quizService;

    @Test
    void getAllQuizzesIfEmpty() throws Exception {
        Mockito.when(quizService.findAll()).thenReturn(new ArrayList<>());
        MvcResult mvcResult = mockMvc.perform(get(quizzesUrl)).andExpect(status().isOk()).andReturn();
        assertEquals("[]", mvcResult.getResponse().getContentAsString());
    }

    @Test
    void getAllQuizzesIfNotEmpty() throws Exception {
        List<Quiz> quizzes = new ArrayList<>();
        quizzes.add(testQuiz1);
        quizzes.add(testQuiz2);
        Mockito.when(quizService.findAll()).thenReturn(quizzes);
        MvcResult mvcResult = mockMvc.perform(get(quizzesUrl)).andExpect(status().isOk()).andReturn();
        assertEquals(mapper.writeValueAsString(quizzes), mvcResult.getResponse().getContentAsString());
    }

    @Test
    void getQuizById() throws Exception {
        Mockito.when(quizService.findById(1)).thenReturn(Optional.of(testQuiz1));
        String url = "/api/quizzes/1";
        MvcResult mvcResult = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();
        assertEquals(mapper.writeValueAsString(testQuiz1), mvcResult.getResponse().getContentAsString());
    }

    @Test
    void solveQuizByIdWithCorrectAnswer() throws Exception {
        Mockito.when(quizService.findById(1)).thenReturn(Optional.of(testQuiz1));
        MvcResult mvcResult = mockMvc.perform(post(solveQuizById).param("answer", "2"))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(successSolve, mvcResult.getResponse().getContentAsString());
    }

    @Test
    void solveQuizByIdWithIncorrectAnswer() throws Exception {
        Mockito.when(quizService.findById(1)).thenReturn(Optional.of(testQuiz1));
        MvcResult mvcResult = mockMvc.perform(post(solveQuizById).param("answer", "1"))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(failSolve, mvcResult.getResponse().getContentAsString());
    }

    // TODO: 04.06.2021 test POST method
//    @Test
//    void createQuiz() throws Exception {
//        MvcResult mvcResult = mockMvc.perform(post(quizzesUrl)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("{\"title\": \"The Java Logo\", " +
//                        "\"text\": \"What is depicted on the Java logo?\", " +
//                        "\"options\": [\"Robot\",\"Tea leaf\",\"Cup of coffee\",\"Bug\"], " +
//                        "\"answer\": 2}"))
//                .andExpect(status().isOk())
//                .andReturn();
//        assertEquals(
//                "{\"id\":1," +
//                        "\"title\":\"The Java Logo\"," +
//                        "\"text\":\"What is depicted on the Java logo?\"," +
//                        "\"options\":[\"Robot\",\"Tea leaf\",\"Cup of coffee\",\"Bug\"]}",
//                mvcResult.getResponse().getContentAsString()
//        );
//    }
}

