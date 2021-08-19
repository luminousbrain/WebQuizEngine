package ru.gontarenko.webquizengine.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "completed_quizzes")
public final class CompletedQuiz {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long completedQuizId;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User whoSolve;

    private Long id;

    private LocalDateTime completedAt;

    public CompletedQuiz() {}

    public CompletedQuiz(Long id, LocalDateTime completedAt, User whoSolve) {
        this.id = id;
        this.completedAt = completedAt;
        this.whoSolve = whoSolve;
    }

    public Long getCompletedQuizId() {
        return completedQuizId;
    }

    public void setCompletedQuizId(Long completedQuizId) {
        this.completedQuizId = completedQuizId;
    }

    public User getWhoSolve() {
        return whoSolve;
    }

    public void setWhoSolve(User whoSolve) {
        this.whoSolve = whoSolve;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }
}
