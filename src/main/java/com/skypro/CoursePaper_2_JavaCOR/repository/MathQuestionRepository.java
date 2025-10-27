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
        // Проверяем, что переданные параметры не являются null
        // Если хотя бы один из них null — возвращаем false (добавление не выполнено)
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
        // Флаг, показывающий, удалось ли удалить элемент
        // Изначально устанавливаем в false (удаление не выполнено)
        boolean removeSuccess = false;

        // Создаём итератор для безопасного удаления элементов из списка
        // (удаление через итератор предотвращает ConcurrentModificationException)
        Iterator<Question> iterator = questionList.iterator();

        // Проходим по всем элементам списка с помощью итератора
        while (iterator.hasNext()) {
            // Получаем текущий элемент списка
            Question current = iterator.next();

            // Проверяем, совпадает ли текущий вопрос с тем, который нужно удалить
            // Сравнение идёт по вопросу и ответу (предполагается, что это уникальный идентификатор)
            if (current.getQuestion().equals(question.getQuestion()) &&
                    current.getAnswer().equals(question.getAnswer())) {
                // Если совпадение найдено — удаляем элемент через итератор
                iterator.remove();
                // Устанавливаем флаг успеха в true
                removeSuccess = true;
            }
        }

        // Возвращаем результат операции: true, если элемент был удалён, иначе false
        return removeSuccess;
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
