package ru.gontarenko.webquizengine.repos;

import org.springframework.data.repository.CrudRepository;
import ru.gontarenko.webquizengine.entities.Quiz;

public interface QuizRepository extends CrudRepository<Quiz, Integer> {

}