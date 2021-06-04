package ru.gontarenko.webquizengine.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.gontarenko.webquizengine.entities.Quiz;
import ru.gontarenko.webquizengine.services.QuizService;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class QuizController {
    private final QuizService quizService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }


    @GetMapping("/quizzes")
    public String getAllQuizzes() {
        try {
            return objectMapper.writeValueAsString(quizService.findAll());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        throw new QuizNotFoundException();
    }

    @GetMapping("/quizzes/{id}")
    public String getQuizById(@PathVariable int id) {
        try {
            Optional<Quiz> quiz = quizService.findById(id);
            if (quiz.isPresent()) {
                return objectMapper.writeValueAsString(quiz.get());
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        throw new QuizNotFoundException();
    }

    @PostMapping("/quizzes/{id}/solve")
    public String solveQuizById(@PathVariable int id, @RequestParam int answer) {
        Optional<Quiz> quizToSolve = quizService.findById(id);
        if (quizToSolve.isEmpty()) {
            throw new QuizNotFoundException();
        }
        Quiz quiz = quizToSolve.get();
        if (quiz.checkAnswer(answer)) {
            return "{\"success\":true,\"feedback\":\"Congratulations, you're right!\"}";
        } else {
            return "{\"success\":false,\"feedback\":\"Wrong answer! Please, try again.\"}";
        }
    }

    @PostMapping(value = "/quizzes", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String createQuiz(@RequestBody Quiz quiz) {
        if (quiz == null) {
            return ""; // ???
        }
        quizService.save(quiz);
        try {
            return objectMapper.writeValueAsString(quiz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "server error";
    }
}
