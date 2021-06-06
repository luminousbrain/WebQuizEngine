package ru.gontarenko.webquizengine.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import ru.gontarenko.webquizengine.entities.Answer;
import ru.gontarenko.webquizengine.entities.Quiz;
import ru.gontarenko.webquizengine.services.QuizService;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class QuizController {
    private final QuizService quizService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public QuizController(@Qualifier("quizServiceImpl") QuizService quizService) {
        this.quizService = quizService;
    }

    // todo Попробовать без objectMapper.writeValueAsString
    @GetMapping("/quizzes")
    public String getAllQuizzes() {
        try {
            return objectMapper.writeValueAsString(quizService.findAll());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "server error";
    }

    @PostMapping(value = "/quizzes")
    public String createQuiz(@RequestBody @Valid Quiz quiz) {
        quizService.save(quiz);
        try {
            return objectMapper.writeValueAsString(quiz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "server error";
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
    public String solveQuizById(@PathVariable int id, @RequestBody Answer answer) {
        Optional<Quiz> quizToSolve = quizService.findById(id);
        if (quizToSolve.isEmpty()) {
            throw new QuizNotFoundException();
        }
        Quiz quiz = quizToSolve.get();
        if (quiz.checkAnswer(answer.getAnswer())) {
            return "{\"success\":true,\"feedback\":\"Congratulations, you're right!\"}";
        } else {
            return "{\"success\":false,\"feedback\":\"Wrong answer! Please, try again.\"}";
        }
    }
}
