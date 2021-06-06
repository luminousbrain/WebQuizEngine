package ru.gontarenko.webquizengine.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gontarenko.webquizengine.entities.Quiz;
import ru.gontarenko.webquizengine.repos.QuizRepository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Collection;
import java.util.Optional;

@Service
public class QuizServiceImpl implements QuizService {
    private final QuizRepository quizRepository;

    @PostConstruct
    public void init() {
        System.out.println("QuizServiceImpl was created!");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("QuizServiceImpl was destroy!");
    }

    @Autowired
    public QuizServiceImpl(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
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
}
