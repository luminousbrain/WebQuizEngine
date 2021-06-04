package ru.gontarenko.webquizengine.entities;

import java.util.List;

public class Quiz {
    private int id;
    private String title;
    private String text;
    private List<String> options;
    private int answer;

    public Quiz() {}

    public Quiz(String title, String text, List<String> options, int answer) {
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

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public boolean checkAnswer(int answer) {
        return this.answer == answer;
    }
}

