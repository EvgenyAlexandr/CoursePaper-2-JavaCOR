package com.skypro.CoursePaper_2_JavaCOR.service;

import com.skypro.CoursePaper_2_JavaCOR.domain.Question;

import java.util.Collection;

public interface ExaminerService {
    Collection<Question> getJavaQuestion(int amount);

    Collection<Question> getMathQuestion(int amount);
}
