package com.skypro.CoursePaper_2_JavaCOR.service;

import com.skypro.CoursePaper_2_JavaCOR.domain.Question;

import java.util.Collection;

public interface QuestionService {

    boolean add(String question, String answer);

    boolean remove(Question question);

    Collection<Question> getAll();

    Question getRandomQuestion();

}
