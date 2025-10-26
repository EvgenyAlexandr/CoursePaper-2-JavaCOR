package com.skypro.CoursePaper_2_JavaCOR.service;

import com.skypro.CoursePaper_2_JavaCOR.domain.Question;
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
        // Создаём список для хранения выбранных вопросов
        List<Question> resultList = new ArrayList<>();

//        // Проверяем, достаточно ли вопросов в системе для формирования выборки
//        // Если общее количество вопросов меньше запрашиваемого, выбрасываем исключение
//        if (questionService.getAll().size() < amount) {
//            throw new IncorrectNumberOfQuestionsInTicket(amount, questionService.getAll().size());
//        }

        // Цикл для выбора заданного количества уникальных вопросов
        for (int i = 0; i < amount; i++) {
            // Флаг для контроля успешного добавления уникального вопроса
            boolean temp = true;
            // Временная переменная для хранения случайно выбранного вопроса
            Question tempQuestion;

            // Бесконечный цикл до тех пор, пока не будет добавлен уникальный вопрос
            while (temp) {
                // Получаем случайный вопрос из сервиса
                tempQuestion = questionService.getRandomQuestion();

                // Проверяем, что вопрос ещё не добавлен в итоговый список
                if (!resultList.contains(tempQuestion)) {
                    // Добавляем уникальный вопрос в результат
                    resultList.add(tempQuestion);
                    // Сбрасываем флаг, чтобы выйти из цикла while
                    temp = false;
                }
                // Если вопрос уже есть в списке, цикл продолжается, и выбирается новый случайный вопрос
            }
        }

        // Возвращаем коллекцию выбранных уникальных вопросов
        return resultList;
    }
}
