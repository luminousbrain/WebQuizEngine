package ru.gontarenko.webquizengine.services;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import ru.gontarenko.webquizengine.entities.Answer;
import ru.gontarenko.webquizengine.entities.CompletedQuiz;
import ru.gontarenko.webquizengine.entities.Quiz;
import ru.gontarenko.webquizengine.entities.User;

import java.util.Optional;

public interface QuizService {
    Optional<Quiz> findById(Long id);

    Page<Quiz> findAll(Integer page, String sortBy);

    Page<CompletedQuiz> findAllCompletedQuizzesByUser(Integer page, String sortBy, User user);

    void save(Quiz quiz);

    ResponseEntity<Void> deleteById(Long id, User user);

    String solveQuiz(Quiz quiz, Answer answer, User user);
}
