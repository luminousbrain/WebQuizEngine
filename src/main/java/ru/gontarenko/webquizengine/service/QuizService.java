package ru.gontarenko.webquizengine.service;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import ru.gontarenko.webquizengine.entity.Answer;
import ru.gontarenko.webquizengine.entity.CompletedQuiz;
import ru.gontarenko.webquizengine.entity.Quiz;
import ru.gontarenko.webquizengine.entity.User;

import java.util.Optional;

public interface QuizService {
    Optional<Quiz> findById(Long id);

    Page<Quiz> findAll(Integer page, String sortBy);

    Page<CompletedQuiz> findAllCompletedQuizzesByUser(Integer page, String sortBy, User user);

    void save(Quiz quiz);

    ResponseEntity<Void> deleteById(Long id, User user);

    String solveQuiz(Quiz quiz, Answer answer, User user);
}
