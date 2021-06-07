package ru.gontarenko.webquizengine.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "quiz_table")
@TypeDef(name = "list", typeClass = ArrayList.class)
@TypeDef(name = "set", typeClass = HashSet.class)
public final class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Column(name = "title")
    private String title;

    @NotBlank(message = "Text is required")
    @Column(name = "text")
    private String text;

    @Size(min = 2, message = "Must be at least two options")
    @NotNull(message = "Options is required")
    @Column(name = "options")
    @Type(type = "list")
    private List<String> options;

    @Column(name = "answer")
    @Type(type = "set")
    private Set<Integer> answer;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Quiz() {}

    public Quiz(String title, String text, List<String> options, Set<Integer> answer) {
        this.title = title;
        this.text = text;
        this.options = options;
        this.answer = answer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public void setAnswer(Set<Integer> answer) {
        this.answer = answer;
    }

    public boolean checkAnswer(Set<Integer> answer) {
        this.answer = this.answer == null ? new HashSet<>() : this.answer;
        answer = answer == null ? new HashSet<>() : answer;
        return answer.containsAll(this.answer) && answer.size() == this.answer.size();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", options=" + options +
                ", answers=" + answer +
                '}';
    }
}