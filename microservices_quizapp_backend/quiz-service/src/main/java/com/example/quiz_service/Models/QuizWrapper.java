package com.example.quiz_service.Models;

public class QuizWrapper {
    private int quizId;
    private String quizTitle;

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public String getQuizTitle() {
        return quizTitle;
    }

    public void setQuizTitle(String quizTitle) {
        this.quizTitle = quizTitle;
    }

    @Override
    public String toString() {
        return "QuizWrapper{" +
                "quizId=" + quizId +
                ", quizTitle='" + quizTitle + '\'' +
                '}';
    }
}
