package ru.gontarenko.webquizengine.entities;

public class QuizResponse {
    private boolean success;
    private String feedback;

    public QuizResponse(boolean success) {
        this.success = success;
        if (success) {
            feedback = "Congratulations, you're right!";
        } else {
            feedback = "Wrong answer! Please, try again.";
        }
    }

    public boolean isSuccess() {
        return success;
    }

    public String getFeedback() {
        return feedback;
    }
}
