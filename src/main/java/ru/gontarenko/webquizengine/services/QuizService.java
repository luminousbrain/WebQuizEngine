package ru.gontarenko.webquizengine.services;

import org.springframework.http.ResponseEntity;
import ru.gontarenko.webquizengine.entities.Quiz;

import java.security.Principal;
import java.util.Collection;
import java.util.Optional;

public interface QuizService {
    Optional<Quiz> findById(int id);
    Collection<Quiz> findAll();
    void save(Quiz quiz);
    ResponseEntity<Void> deleteById(int id, Principal principal);
}
