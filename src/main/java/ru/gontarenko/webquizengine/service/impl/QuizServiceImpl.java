package ru.gontarenko.webquizengine.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.gontarenko.webquizengine.entity.Answer;
import ru.gontarenko.webquizengine.entity.CompletedQuiz;
import ru.gontarenko.webquizengine.entity.Quiz;
import ru.gontarenko.webquizengine.entity.User;
import ru.gontarenko.webquizengine.repository.CompletedQuizRepository;
import ru.gontarenko.webquizengine.repository.QuizRepository;
import ru.gontarenko.webquizengine.service.QuizService;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public final class QuizServiceImpl implements QuizService {
    private final QuizRepository quizRepository;
    private final CompletedQuizRepository completedQuizRepository;

    @Autowired
    public QuizServiceImpl(
            QuizRepository quizRepository,
            CompletedQuizRepository completedQuizRepository
    ) {
        this.quizRepository = quizRepository;
        this.completedQuizRepository = completedQuizRepository;
    }

    @Override
    public Optional<Quiz> findById(Long id) {
        return quizRepository.findById(id);
    }

    @Override
    public Page<Quiz> findAll(Integer page, String sortBy) {
        return quizRepository.findAll(
                PageRequest.of(
                        page, 10,
                        Sort.Direction.ASC, sortBy
                ));
    }

    @Override
    public Page<CompletedQuiz> findAllCompletedQuizzesByUser(Integer page, String sortBy, User user) {
        return completedQuizRepository.findByWhoSolve(
                user,
                PageRequest.of(
                        page, 10,
                        Sort.Direction.DESC, sortBy
                ));
    }

    @Override
    public void save(Quiz quiz) {
        quizRepository.save(quiz);
    }

    @Override
    public ResponseEntity<Void> deleteById(Long id, User user) {
        Optional<Quiz> byId = quizRepository.findById(id);
        if (byId.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Quiz quiz = byId.get();
        if (!quiz.getUser().getId().equals(user.getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        quizRepository.delete(quiz);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public String solveQuiz(Quiz quiz, Answer answer, User user) {
        if (quiz.checkAnswer(answer.getAnswer())) {
            completedQuizRepository.save(new CompletedQuiz(quiz.getId(), LocalDateTime.now(), user));
            return "{\"success\":true,\"feedback\":\"Congratulations, you're right!\"}";
        } else {
            return "{\"success\":false,\"feedback\":\"Wrong answer! Please, try again.\"}";
        }
    }
}
