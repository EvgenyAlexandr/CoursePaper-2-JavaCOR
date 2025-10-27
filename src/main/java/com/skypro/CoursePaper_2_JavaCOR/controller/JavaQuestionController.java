package com.skypro.CoursePaper_2_JavaCOR.controller;

import com.skypro.CoursePaper_2_JavaCOR.domain.Question;
import com.skypro.CoursePaper_2_JavaCOR.service.JavaQuestionService;
import com.skypro.CoursePaper_2_JavaCOR.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


@RestController
public class JavaQuestionController {

    @Autowired
    private QuestionService javaQuestionService;

    // Добавляем вопрос
    // - question текст вопроса, передаётся через URL-путь
    // - answer правильный ответ на вопрос, передаётся через URL-путь
    @GetMapping("/exam/java/add/{question}/{answer}")
    public String addQuestion(@PathVariable("question") String question,
                              @PathVariable("answer") String answer) {
        // Пытаемся добавить вопрос через сервис
        boolean addSuccess = javaQuestionService.add(question, answer);

        // Возвращаем соответствующий ответ в зависимости от результата операции
        if (addSuccess) {
            return "*Вопрос добавлен*";
        } else {
            return "*Вопрос уже существует*";
        }
    }

    // Удалить вопрос
    @GetMapping("/exam/java/remove/{question}/{answer}")
    public String removeQuestion(@PathVariable("question") String question,
                                 @PathVariable("answer") String answer) {
        // Создаём объект вопроса и пытаемся удалить его через сервис
        boolean removeSuccess = javaQuestionService.remove(new Question(question, answer));

        // Возвращаем соответствующий ответ в зависимости от результата операции
        if (removeSuccess) {
            return "*Вопрос удалён*";
        } else {
            return "*Вопрос не найден*";
        }
    }

    // Отображение Всех вопросов
    @GetMapping("/exam/java")
    public Collection<Question> getAllQuestion() {
        // Получаем все вопросы через сервис и возвращаем их
        return javaQuestionService.getAll();
    }
}





