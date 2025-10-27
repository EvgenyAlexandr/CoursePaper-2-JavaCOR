package com.skypro.CoursePaper_2_JavaCOR.repository;

import com.skypro.CoursePaper_2_JavaCOR.domain.Question;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Component
public class JavaQuestionRepository implements QuestionRepository {
    private final List<Question> questionList = new ArrayList<>();

    @Override
    public boolean add(String question, String answer) {
//        boolean questionExists = false;
//        for (Question value : questionList) {
//            if (value.getQuestion().equals(question)) {
//                if (value.getAnswer().equals(answer)) {
//                    questionExists = true;
//                    break;
//                }
//            }
//        }
//        if (!questionExists) {
//            questionList.add(new Question(question, answer));
//            return true;
//        } else {
//            return false;
//        }

        // Проверка входных параметров
        if (question == null || answer == null) {
            return false;
        }

        // Проверяем существование вопроса с таким же текстом и ответом
        for (Question existing : questionList) {
            if (existing.getQuestion().equals(question) &&
                    existing.getAnswer().equals(answer)) {
                return false; // Вопрос уже существует
            }
        }

        // Добавляем новый вопрос
        questionList.add(new Question(question, answer));
        return true;
    }

    @Override
    public boolean remove(Question question) {
//        boolean removeSuccses = false;
//        for (int i = 0; i < questionList.size(); i++) {
//            if (questionList.get(i).getQuestion().equals(question.getQuestion())) {
//                if (questionList.get(i).getAnswer().equals(question.getAnswer())) {
//                    questionList.remove(i);
//                    removeSuccses = true;
//                }
//            }
//        }
//        return removeSuccses;


        // Проверяем, что переданный объект question не является null.
        if (question == null) {
            return false;
        }

        // Создаём итератор для обхода коллекции questionList.
        Iterator<Question> iterator = questionList.iterator();

        // Начинаем последовательный обход элементов коллекции.
        while (iterator.hasNext()) {
            // Получаем текущий элемент коллекции для проверки.
            Question current = iterator.next();

            // Ищем совпадение текст вопроса и текст ответа
            if (current.getQuestion().equals(question.getQuestion())
                    && current.getAnswer().equals(question.getAnswer())) {

                // Удаление.
                iterator.remove();

                // Сразу возвращаем true, так как элемент успешно найден и удалён.
                // Дальнейший обход коллекции не требуется.
                return true;
            }
        }

        // Если цикл завершился, а элемент не был найден, возвращаем false.
        return false;
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
