package ru.gontarenko.webquizengine.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gontarenko.webquizengine.entities.Quiz;
import ru.gontarenko.webquizengine.entities.QuizResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class QuizController {
    private Quiz testQuiz;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public QuizController() {
        List<String> options = new ArrayList<>();
        options.add("Robot");
        options.add("Tea leaf");
        options.add("Cup of coffee");
        options.add("Bug");
        testQuiz = new Quiz("The Java Logo", "What is depicted on the Java logo?", options);
    }

    @GetMapping("/quiz")
    public String quiz() {
        try {
            return objectMapper.writeValueAsString(testQuiz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "Server error";
    }

    @PostMapping("/quiz")
    public String quiz(@RequestParam("answer") int answer) {
        // @RequestBody() Map<String, String> payload
        // String answer = payload.get("answer");
        // Integer.parseInt(answer) == 2
        try {
            return objectMapper.writeValueAsString(new QuizResponse(answer == 2));
        } catch (JsonProcessingException | NumberFormatException e) {
            e.printStackTrace();
        }
        return "Server error";
    }
}
