package com.skypro.CoursePaper_2_JavaCOR.domain;

public class Question {
    private final String question;  // Вопрос
    private final String answer;    // Ответ

    public Question(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}
