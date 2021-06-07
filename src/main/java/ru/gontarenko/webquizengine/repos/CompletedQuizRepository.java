package ru.gontarenko.webquizengine.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gontarenko.webquizengine.entities.CompletedQuiz;
import ru.gontarenko.webquizengine.entities.User;

@Repository
public interface CompletedQuizRepository extends JpaRepository<CompletedQuiz, Long> {
    Page<CompletedQuiz> findByWhoSolve(User user, Pageable pageRequest);
}