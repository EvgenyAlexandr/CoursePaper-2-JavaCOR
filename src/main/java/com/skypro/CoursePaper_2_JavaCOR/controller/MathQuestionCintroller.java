package com.skypro.CoursePaper_2_JavaCOR.controller;

import com.skypro.CoursePaper_2_JavaCOR.domain.Question;
import com.skypro.CoursePaper_2_JavaCOR.service.MathQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class MathQuestionCintroller {
    @Autowired
    private MathQuestionService mathQuestionService;

    // Добавляем вопрос
    // - question текст вопроса, передаётся через URL-путь
    // - answer правильный ответ на вопрос, передаётся через URL-путь
        @GetMapping("/exam/math/add/{question}/{answer}")
    public String addQuestion(@PathVariable("question") String question,
                              @PathVariable("answer") String answer) {
        // Пытаемся добавить вопрос через сервис
        boolean addSuccess = mathQuestionService.add(question, answer);

        // Возвращаем результат операции
        if (addSuccess) {
            return "*Вопрос добавлен*";  // Успешное добавление
        } else {
            return "*Вопрос уже существует*";  // Вопрос дублируется
        }
    }

    @GetMapping("/exam/math/remove/{question}/{answer}")
    public String removeQuestion(@PathVariable("question") String question,
                                 @PathVariable("answer") String answer) {
        // Создаем объект вопроса и пытаемся удалить его через сервис
        boolean removeSuccess = mathQuestionService.remove(new Question(question, answer));

        // Возвращаем результат операции
        if (removeSuccess) {
            return "*Вопрос удалён*";  // Успешное удаление
        } else {
            return "*Вопрос не найден*";  // Запись отсутствует в системе
        }
    }

    @GetMapping("/exam/math")
    public Collection<Question> getAllQuestion() {
        // Получаем полный список вопросов через сервис
        return mathQuestionService.getAll();
    }

}
