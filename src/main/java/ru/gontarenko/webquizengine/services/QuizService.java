package ru.gontarenko.webquizengine.services;

import ru.gontarenko.webquizengine.entities.Quiz;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface QuizService {
    Optional<Quiz> findById(int id);
    Collection<Quiz> findAll(); // todo: test List ???
    void save(Quiz quiz);
}
