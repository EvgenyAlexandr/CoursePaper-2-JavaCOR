package com.skypro.CoursePaper_2_JavaCOR.unit;

import com.skypro.CoursePaper_2_JavaCOR.domain.Question;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class QuestionServiceFixture {

    public static Collection<Question> getEmptyQuestionService() {
        List<Question> result = new ArrayList<>();
        return result;
    }

    public static Collection<Question> get1QuestionInQuestionService() {
        List<Question> result = new ArrayList<>();
        result.add(new Question("Вопрос 1", "Ответ 1"));
        return result;
    }

    public static Question get1Question() {
        return new Question("Вопрос 1", "Ответ 1");
    }
}
