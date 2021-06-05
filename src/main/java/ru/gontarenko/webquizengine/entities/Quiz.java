package ru.gontarenko.webquizengine.entities;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Quiz {
    private int id;
    @NotBlank
    private String title;
    @NotBlank(message = "Must be not empty")
    private String text;
    @Size(min = 2)
    @NotNull
    private List<String> options;
    private Set<Integer> answer;

    public Quiz() {}

    public Quiz(String title, String text, List<String> options, Set<Integer> answer) {
        this.title = title;
        this.text = text;
        this.options = options;
        this.answer = answer;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
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