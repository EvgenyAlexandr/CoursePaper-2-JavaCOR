package com.skypro.CoursePaper_2_JavaCOR.exceptions;

public class IncorrectNumberOfQuestionsInTicket extends RuntimeException {
    private final int amount;
    private final int countQuestions;

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
