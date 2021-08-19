package ru.gontarenko.webquizengine.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gontarenko.webquizengine.entity.CompletedQuiz;
import ru.gontarenko.webquizengine.entity.User;

@Repository
public interface CompletedQuizRepository extends JpaRepository<CompletedQuiz, Long> {
    Page<CompletedQuiz> findByWhoSolve(User user, Pageable pageRequest);
}