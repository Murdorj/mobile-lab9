package com.example.lab9;

import java.io.Serializable;

public class Question implements Serializable {
    private String questionText;
    private String[] answers;
    private int correctAnswerIndex;

    // Constructor, getters, and setters
    public Question(String questionText, String[] answers, int correctAnswerIndex) {
        this.questionText = questionText;
        this.answers = answers;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String[] getAnswers() {
        return answers;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }
}