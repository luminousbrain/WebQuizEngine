package ru.gontarenko.webquizengine.services.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.gontarenko.webquizengine.entities.Quiz;
import ru.gontarenko.webquizengine.services.QuizService;

import java.security.Principal;
import java.util.*;

@Service
public class QuizServiceWithNoDbImpl implements QuizService {
    private Map<Integer, Quiz> quizMap; // tmp database

    public QuizServiceWithNoDbImpl() {
        this.quizMap = new LinkedHashMap<>();
    }

    @Override
    public Optional<Quiz> findById(int id) {
        Quiz quiz = quizMap.get(id);
        return quiz == null ? Optional.empty() : Optional.of(quiz);
    }

    @Override
    public Collection<Quiz> findAll() {
        return quizMap.values();
    }

    @Override
    public void save(Quiz quiz) {
        quiz.setId(quizMap.size() + 1);
        quizMap.put(quiz.getId(), quiz);
    }

    @Override
    public ResponseEntity<Void> deleteById(int id, Principal principal) {
        return null;
    }
}
