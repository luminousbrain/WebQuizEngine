package ru.gontarenko.webquizengine.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gontarenko.webquizengine.entities.Answer;
import ru.gontarenko.webquizengine.entities.Quiz;
import ru.gontarenko.webquizengine.services.QuizService;
import ru.gontarenko.webquizengine.services.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {
    private final QuizService quizService;
    private final UserService userService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public QuizController(@Qualifier("quizServiceImpl") QuizService quizService,
                          UserService userService) {
        this.quizService = quizService;
        this.userService = userService;
    }

    // todo Попробовать без objectMapper.writeValueAsString
    @GetMapping()
    public String getAllQuizzes() {
        try {
            return objectMapper.writeValueAsString(quizService.findAll());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "server error";
    }

    @PostMapping()
    public String createQuiz(@RequestBody @Valid Quiz quiz, Principal principal) {
        quiz.setUser(userService.findByEmail(principal.getName()));
        quizService.save(quiz);
        try {
            return objectMapper.writeValueAsString(quiz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "server error";
    }

    @GetMapping("/{id}")
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuizById(@PathVariable int id, Principal principal) {
        return quizService.deleteById(id, principal);
    }


    @PostMapping("/{id}/solve")
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
