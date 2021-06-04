package ru.gontarenko.webquizengine.entities;

import java.util.List;

public class Quiz {
    private String title;
    private String text;
    private List<String> options;

    public Quiz(String title, String text, List<String> options) {
        this.title = title;
        this.text = text;
        this.options = options;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public List<String> getOptions() {
        return options;
    }
}

