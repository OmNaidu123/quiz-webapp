package com.example.quiz_service.Models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer quizId;
    private String quizTitle;

    @ElementCollection
    private List<Integer> questions;

    public List<Integer> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Integer> questions) {
        this.questions = questions;
    }

    public Integer getQuizId() {
        return quizId;
    }

    public void setQuizId(Integer quizId) {
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
        return "Quiz{" +
                "questions=" + questions +
                ", quizId=" + quizId +
                ", quizTitle='" + quizTitle + '\'' +
                '}';
    }
}
