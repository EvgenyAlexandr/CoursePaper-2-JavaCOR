package com.skypro.CoursePaper_2_JavaCOR.service;

import com.skypro.CoursePaper_2_JavaCOR.domain.Question;
import com.skypro.CoursePaper_2_JavaCOR.exceptions.IncorrectNumberOfQuestionsInTicket;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class ExaminerServiceImpl implements ExaminerService {

    private final JavaQuestionService javaQuestionService;
    private final MathQuestionService mathQuestionService;

    public ExaminerServiceImpl(JavaQuestionService javaQuestionService, MathQuestionService mathQuestionService) {
        this.javaQuestionService = javaQuestionService;
        this.mathQuestionService = mathQuestionService;
    }

    @Override
    public Collection<Question> getJavaQuestion(int amount) {
        List<Question> resultList = new ArrayList<>();

        // Если общее количество вопросов меньше запрашиваемого, выбрасываем исключение
        if (javaQuestionService.getAll().size() < amount) {
            throw new IncorrectNumberOfQuestionsInTicket(amount, javaQuestionService.getAll().size());
        }

        // Цикл для выбора заданного количества уникальных вопросов
        for (int i = 0; i < amount; i++) {
            Question tempQuestion;      // Временная переменная для хранения случайно выбранного вопроса

            // Цикл do-while для получения уникального вопроса
            do {
                // Получаем случайный вопрос из сервиса
                tempQuestion = javaQuestionService.getRandomQuestion();
            } while (resultList.contains(tempQuestion)); // Повторяем, пока вопрос не будет уникальным

            // Добавляем уникальный вопрос в результат
            resultList.add(tempQuestion);
        }

        // Возвращаем коллекцию выбранных уникальных вопросов
        return resultList;
    }

    @Override
    public Collection<Question> getMathQuestion(int amount) {
        List<Question> resultList = new ArrayList<>();

        // Если запрошенное количество больше доступного — выбрасываем исключение
        if (mathQuestionService.getAll().size() < amount) {
            throw new IncorrectNumberOfQuestionsInTicket(amount, mathQuestionService.getAll().size());
        }

        // Цикл для выбора необходимого количества уникальных вопросов
        for (int i = 0; i < amount; i++) {
            Question candidateQuestion; // Временная переменная для хранения выбранного вопроса

            // Используем do-while: сначала получаем вопрос, затем проверяем уникальность
            do {
                // Получаем случайный вопрос из сервиса
                candidateQuestion = mathQuestionService.getRandomQuestion();
            } while (resultList.contains(candidateQuestion)); // Повторяем, если вопрос уже есть в списке

            // Добавляем уникальный вопрос в итоговый список
            resultList.add(candidateQuestion);
        }

        // Возвращаем коллекцию выбранных уникальных вопросов
        return resultList;
    }
}
