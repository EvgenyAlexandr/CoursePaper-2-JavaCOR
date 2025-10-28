package com.skypro.CoursePaper_2_JavaCOR.repository;

import com.skypro.CoursePaper_2_JavaCOR.domain.Question;

import java.util.Collection;

public interface QuestionRepository {

    boolean add(String question, String answer);

    boolean remove(Question question);

    Collection<Question> getAll();

    Question getQuestion(int i);
}
