package com.skypro.CoursePaper_2_JavaCOR.service;

import com.skypro.CoursePaper_2_JavaCOR.domain.Question;
import com.skypro.CoursePaper_2_JavaCOR.exceptions.IncorrectCallGetRandomQuestionBCQuestionServiceEmpty;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class JavaQuestionService implements QuestionService {
    public List<Question> questionPull = new ArrayList<Question>();

    @Override
    public boolean add(String question, String answer) {
        // Проверяем, не являются ли входные параметры null
        if (question == null || answer == null) {
            throw new IllegalArgumentException("Вопрос и ответ не могут быть null");
        }

        boolean questionExists = false; // Флаг для отслеживания наличия точно такой же пары "вопрос-ответ"

        // Проходим по всем существующим вопросам в коллекции
        for (Question value : questionPull) {
            // Сначала проверяем совпадение вопроса (основное условие)
            if (value.getQuestion().equals(question)) {
                // Если вопрос совпадает, проверяем совпадение ответа
                if (value.getAnswer().equals(answer)) {
                    // Нашли полную копию — отмечаем и прерываем поиск
                    questionExists = true;
                    break;
                }
                // Примечание: если вопрос совпадает, но ответ разный,
                // мы НЕ считаем это дубликатом (по текущей логике метода)
            }
        }

        // Если точно такой же пары не найдено — добавляем новую запись
        if (!questionExists) {
            questionPull.add(new Question(question, answer));
            return true; // Успешное добавление
        } else {
            // Такая же пара уже существует — отклоняем добавление
            return false; // Дубликат найден
        }
    }

    @Override
    public boolean add(Question question) {
        // Флаг, показывающий, существует ли уже такой вопрос в коллекции
        boolean questionExists = false;

        // Перебираем все вопросы в коллекции questionPull
        for (Question value : questionPull) {
            // Проверяем, совпадает ли текст текущего вопроса из коллекции с проверяемым вопросом
            if (value.getQuestion().equals(question.getQuestion())) {
                // Если текст вопроса совпал, проверяем совпадение ответа
                if (value.getAnswer().equals(question.getAnswer())) {
                    // При полном совпадении вопроса и ответа устанавливаем флаг и прерываем цикл
                    questionExists = true;
                    break; // Выходим из цикла, так как дубликат найден
                }
            }
        }

        // После завершения цикла проверяем значение флага
        if (!questionExists) {
            // Если дубликат не найден, добавляем новый вопрос в коллекцию
            questionPull.add(question);
            // Возвращаем true, сигнализируя об успешном добавлении
            return true;
        } else {
            // Если дубликат был обнаружен, возвращаем false
            return false;
        }
    }

    @Override
    public boolean remove(Question question) {
        // Флаг, показывающий, удалось ли удалить вопрос из коллекции
        boolean removeSuccess = false;

        // Перебираем все элементы коллекции questionPull по индексу
        // Используем классический for с индексом, так как будем удалять элементы по позиции
        for (int i = 0; i < questionPull.size(); i++) {
            // Проверяем, совпадает ли текст текущего вопроса с искомым
            if (questionPull.get(i).getQuestion().equals(question.getQuestion())) {
                // Удаляем вопрос из коллекции по текущему индексу
                // При удалении элемент сдвигается, и следующий элемент занимает его позицию
                questionPull.remove(i);

                // Устанавливаем флаг успеха удаления
                removeSuccess = true;
            }
        }

        // Возвращаем результат операции: true — если хотя бы один вопрос был удалён, false — если ни одного
        return removeSuccess;
    }

    @Override
    public Collection<Question> getAll() {
        return questionPull;
    }

    @Override
    public Question getRandomQuestion() {
        if (questionPull.isEmpty()) {
            throw new IncorrectCallGetRandomQuestionBCQuestionServiceEmpty();
        }
        Random random = new Random();
        int randomNumber = random.nextInt(questionPull.size());
        return questionPull.get(randomNumber);
    }



}
