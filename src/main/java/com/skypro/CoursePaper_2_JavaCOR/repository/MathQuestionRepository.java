package com.skypro.CoursePaper_2_JavaCOR.repository;

import com.skypro.CoursePaper_2_JavaCOR.domain.Question;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Component
public class MathQuestionRepository implements QuestionRepository {
    private final List<Question> questionList = new ArrayList<>();

    @Override
    public boolean add(String question, String answer) {
        // Проверка входных параметров
        if (question == null || answer == null) {
            return false;
        }

        // Проходим по всем существующим вопросам в списке questionList
        // Цель — проверить, нет ли уже точно такого же вопроса с таким же ответом
        for (Question value : questionList) {
            // Сравниваем текущий вопрос и ответ из списка с передаваемыми параметрами
            // Если нашли дубликат — возвращаем false (добавление не нужно)
            if (value.getQuestion().equals(question) &&
                    value.getAnswer().equals(answer)) {
                return false;
            }
        }

        // Если дубликат не найден — создаём новый объект Question
        // и добавляем его в список questionList
        questionList.add(new Question(question, answer));

        // Возвращаем true, так как добавление выполнено успешно
        return true;
    }

    @Override
    public boolean remove(Question question) {
        // Проверка на null-аргумент
        if (question == null) return false;

        boolean removeSuccess = false; // Флаг успешного удаления

        // Обход списка с конца для безопасного удаления элементов
        for (int i = questionList.size() - 1; i >= 0; i--) {
            Question current = questionList.get(i); // Получение текущего элемента

            // Проверка на null и совпадение полей вопроса и ответа
            if (current != null &&
                    current.getQuestion().equals(question.getQuestion()) &&
                    current.getAnswer().equals(question.getAnswer())) {

                questionList.remove(i); // Удаление элемента по индексу
                removeSuccess = true;   // Установка флага успеха
            }
        }
        return removeSuccess; // Возврат результата удаления
    }


    @Override
    public Collection<Question> getAll() {
        return questionList;
    }

    @Override
    public Question getQuestion(int i) {
        return questionList.get(i);
    }
}
