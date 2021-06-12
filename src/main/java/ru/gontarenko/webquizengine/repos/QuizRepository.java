package ru.gontarenko.webquizengine.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gontarenko.webquizengine.entities.Quiz;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

}