package com.skypro.CoursePaper_2_JavaCOR.service;

import com.skypro.CoursePaper_2_JavaCOR.domain.Question;
import com.skypro.CoursePaper_2_JavaCOR.exceptions.IncorrectNumberOfQuestionsInTicket;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class ExaminerServiceImpl implements ExaminerService {

    private final QuestionService questionService;

    public ExaminerServiceImpl(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    public Collection<Question> getQuestion(int amount) {
        List<Question> resultList = new ArrayList<>();

        if (questionService.getAll().size() < amount) {
            throw new IncorrectNumberOfQuestionsInTicket(amount, questionService.getAll().size());
        }

        for (int i = 0; i < amount; i++) {
            boolean temp = true;
            Question tempQuestion;

            while (temp) {
                tempQuestion = questionService.getRandomQuestion();

                if (!resultList.contains(tempQuestion)) {
                    resultList.add(tempQuestion);
                    temp = false;
                }
            }
        }
        return resultList;
    }
}
