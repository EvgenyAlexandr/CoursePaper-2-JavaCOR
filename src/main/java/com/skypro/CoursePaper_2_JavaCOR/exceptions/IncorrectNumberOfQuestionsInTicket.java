package com.skypro.CoursePaper_2_JavaCOR.exceptions;

// Неверный номер запроса в билете
public class IncorrectNumberOfQuestionsInTicket extends RuntimeException {
    private final int amount;           // Сумма
    private final int countQuestions;   // Количество Вопросов

    public IncorrectNumberOfQuestionsInTicket(int amount, int countQuestions) {
        this.amount = amount;
        this.countQuestions = countQuestions;
    }

    public int getAmount() {
        return amount;
    }

    public int getCountQuestions() {
        return countQuestions;
    }
}
