package com.skypro.CoursePaper_2_JavaCOR.repository;

import com.skypro.CoursePaper_2_JavaCOR.domain.Question;

import java.util.Collection;

public interface QuestionRepository {

    boolean add(String question, String answer);    // Добавить

    boolean remove(Question question);              // Удалить

    Collection<Question> getAll();                  // Все

    Question getQuestion(int i);                    // Получить вопрос
}
