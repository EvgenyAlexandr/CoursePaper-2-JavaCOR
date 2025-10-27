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
