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
        if (question == null || answer == null) {
            throw new IllegalArgumentException("Вопрос и ответ не могут быть null");
        }

        boolean questionExists = false;

        for (Question value : questionPull) {
            if (value.getQuestion().equals(question)) {
                if (value.getAnswer().equals(answer)) {
                    questionExists = true;
                    break;
                }
            }
        }

        if (!questionExists) {
            questionPull.add(new Question(question, answer));
            return true; // Успешное добавление
        } else {
            return false; // Дубликат найден
        }
    }

    @Override
    public boolean add(Question question) {
        boolean questionExists = false;

        for (Question value : questionPull) {
            if (value.getQuestion().equals(question.getQuestion())) {
                if (value.getAnswer().equals(question.getAnswer())) {
                    questionExists = true;
                    break; // Выходим из цикла, так как дубликат найден
                }
            }
        }

        // После завершения цикла проверяем значение флага
        if (!questionExists) {
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
        boolean removeSuccess = false;

        for (int i = 0; i < questionPull.size(); i++) {
            if (questionPull.get(i).getQuestion().equals(question.getQuestion())) {
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
