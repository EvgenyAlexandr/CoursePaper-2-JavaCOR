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

//        // Флаг, показывающий, существует ли уже такая пара «вопрос-ответ» в списке
//        boolean questionExists = false;
//
//        // Проходим по всем элементам в списке вопросов
//        for (Question value : questionList) {
//            // Сначала проверяем, совпадает ли текст вопроса с переданным параметром
//            if (value.getQuestion().equals(question)) {
//                // Если вопрос совпадает, проверяем, совпадает ли ответ
//                if (value.getAnswer().equals(answer)) {
//                    // Найден точный дубликат: и вопрос, и ответ совпадают
//                    questionExists = true;
//                    // Прерываем цикл, так как дальнейшая проверка не нужна
//                    break;
//                }
//            }
//        // Если дубликат не найден, добавляем новую пару в список
//        if (!questionExists) {
//            questionList.add(new Question(question, answer));
//            // Возвращаем true, сигнализируя об успешном добавлении
//            return true;
//        } else {
//            // Если дубликат найден, не добавляем элемент и возвращаем false
//            return false;
//        }

        if (question == null || answer == null) {
            return false;
        }

        for (Question value : questionList) {
            if (value.getQuestion().equals(question) &&
                    value.getAnswer().equals(answer)) {
                return false;
            }
        }
        questionList.add(new Question(question, answer));
        return true;

    }



    @Override
    public boolean remove(Question question) {
//        // Флаг, указывающий на успешность удаления элемента
//        boolean removeSuccess = false;
//
//        // Создаём итератор для обхода списка вопросов
//        // Использование итератора предпочтительно, так как позволяет безопасно удалять элементы во время обхода
//        Iterator<Question> iterator = questionList.iterator();
//
//        // Проходим по всем элементам списка, пока есть следующие элементы
//        while (iterator.hasNext()) {
//            // Получаем текущий элемент списка
//            Question current = iterator.next();
//
//            // Проверяем, совпадает ли текущий вопрос с тем, который нужно удалить
//            // Сравнение идёт по двум полям: тексту вопроса и ответу
//            // Это гарантирует, что удаляется именно нужная пара "вопрос-ответ"
//            if (current.getQuestion().equals(question.getQuestion()) &&
//                    current.getAnswer().equals(question.getAnswer())) {
//
//                // Удаляем текущий элемент из списка через итератор
//                iterator.remove();
//
//                // Устанавливаем флаг успеха в true, так как удаление выполнено
//                removeSuccess = true;
//            }
//        }
//
//        // Возвращаем результат операции: true, если элемент был удалён, false — иначе
//        return removeSuccess;

        boolean removeSuccess = false;

        Iterator<Question> iterator = questionList.iterator();
        while (iterator.hasNext()) {
            Question current = iterator.next();
            if (current.getQuestion().equals(question.getQuestion()) &&
                    current.getAnswer().equals(question.getAnswer())) {
                iterator.remove();
                removeSuccess = true;
            }
        }
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
