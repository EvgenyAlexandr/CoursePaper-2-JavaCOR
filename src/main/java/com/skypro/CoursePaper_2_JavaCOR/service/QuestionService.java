package com.skypro.CoursePaper_2_JavaCOR.service;

import com.skypro.CoursePaper_2_JavaCOR.domain.Question;

import java.util.Collection;

public interface QuestionService {

    boolean add(String question, String answer);  // Добавить

    boolean remove(Question question);           // Удалить

    Collection<Question> getAll();              // Все вопросы

    Question getRandomQuestion();               // Получить Случайный вопрос

}
