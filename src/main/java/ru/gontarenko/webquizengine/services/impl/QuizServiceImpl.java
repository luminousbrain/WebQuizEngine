package ru.gontarenko.webquizengine.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.gontarenko.webquizengine.entities.Quiz;
import ru.gontarenko.webquizengine.repos.QuizRepository;
import ru.gontarenko.webquizengine.repos.UserRepository;
import ru.gontarenko.webquizengine.services.QuizService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.security.Principal;
import java.util.Collection;
import java.util.Optional;

@Service
public class QuizServiceImpl implements QuizService {
    private final QuizRepository quizRepository;
    private final UserRepository userRepository;

    @PostConstruct
    public void init() {
        System.out.println("QuizServiceImpl was created!");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("QuizServiceImpl was destroy!");
    }

    @Autowired
    public QuizServiceImpl(QuizRepository quizRepository, UserRepository userRepository) {
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Optional<Quiz> findById(int id) {
        return quizRepository.findById(id);
    }

    @Override
    public Collection<Quiz> findAll() {
        return (Collection<Quiz>) quizRepository.findAll();
    }

    @Override
    public void save(Quiz quiz) {
        quizRepository.save(quiz);
    }

    @Override
    public ResponseEntity<Void> deleteById(int id, Principal principal) {
        Optional<Quiz> byId = quizRepository.findById(id);
        if (byId.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Quiz quiz = byId.get();
        if (quiz.getUser().getId() != userRepository.findByEmail(principal.getName()).get().getId()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        quizRepository.delete(quiz);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
