package ru.gontarenko.webquizengine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gontarenko.webquizengine.entity.Answer;
import ru.gontarenko.webquizengine.entity.CompletedQuiz;
import ru.gontarenko.webquizengine.entity.Quiz;
import ru.gontarenko.webquizengine.exception.QuizNotFoundException;
import ru.gontarenko.webquizengine.service.QuizService;
import ru.gontarenko.webquizengine.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {
    private final QuizService quizService;
    private final UserService userService;

    @Autowired
    public QuizController(
            @Qualifier("quizServiceImpl") QuizService quizService,
            UserService userService
    ) {
        this.quizService = quizService;
        this.userService = userService;
    }

    @GetMapping()
    public Page<Quiz> getAllQuizzes(
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<String> sortBy
    ) {
        return quizService.findAll(page.orElse(0), sortBy.orElse("id"));
    }

    @GetMapping("/completed")
    public Page<CompletedQuiz> getCompletedQuizzes(
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<String> sortBy,
            Principal principal
    ) {
        return quizService.findAllCompletedQuizzesByUser(
                page.orElse(0),
                sortBy.orElse("completedAt"),
                userService.findByEmail(principal.getName())
        );
    }

    @GetMapping("/{id}")
    public Quiz getQuizById(@PathVariable Long id) {
        Optional<Quiz> quiz = quizService.findById(id);
        if (quiz.isPresent()) {
            return quiz.get();
        }
        throw new QuizNotFoundException();
    }

    @PostMapping()
    public Quiz createQuiz(@RequestBody @Valid Quiz quiz, Principal principal) {
        quiz.setUser(userService.findByEmail(principal.getName()));
        quizService.save(quiz);
        return quiz;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuizById(@PathVariable Long id, Principal principal) {
        return quizService.deleteById(id, userService.findByEmail(principal.getName()));
    }


    @PostMapping("/{id}/solve")
    public String solveQuizById(@PathVariable Long id, @RequestBody Answer answer, Principal principal) {
        Optional<Quiz> quizToSolve = quizService.findById(id);
        Quiz quiz = quizToSolve.orElseThrow(
                QuizNotFoundException::new
        );
        return quizService.solveQuiz(
                quiz,
                answer,
                userService.findByEmail(principal.getName())
        );
    }
}
