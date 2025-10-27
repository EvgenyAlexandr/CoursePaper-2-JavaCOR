package com.skypro.CoursePaper_2_JavaCOR.service;

import com.skypro.CoursePaper_2_JavaCOR.domain.Question;
import com.skypro.CoursePaper_2_JavaCOR.exceptions.IncorrectCallGetRandomQuestionBCQuestionServiceEmpty;
import com.skypro.CoursePaper_2_JavaCOR.repository.JavaQuestionRepository;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class JavaQuestionService implements QuestionService {
    private final JavaQuestionRepository javaQuestionRepository;

    public JavaQuestionService(JavaQuestionRepository javaQuestionRepository) {
        this.javaQuestionRepository = javaQuestionRepository;
    }

    @Override
    public boolean add(String question, String answer) {
        return javaQuestionRepository.add(question, answer);
    }

//    @Override
//    public boolean add(Question question) {
//        return false;
//    }

    @Override
    public boolean remove(Question question) {
        return javaQuestionRepository.remove(question);
    }

    @Override
    public Collection<Question> getAll() {
        return javaQuestionRepository.getAll();
    }

    @Override
    public Question getRandomQuestion() {
        if (javaQuestionRepository.getAll().isEmpty()) {
            throw new IncorrectCallGetRandomQuestionBCQuestionServiceEmpty();
        }
        Random random = new Random();
        int randomNumber = random.nextInt(javaQuestionRepository.getAll().size());
        return javaQuestionRepository.getQuestion(randomNumber);
    }
}
