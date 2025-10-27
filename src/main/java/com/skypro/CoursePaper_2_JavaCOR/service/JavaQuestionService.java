package com.skypro.CoursePaper_2_JavaCOR.service;

import com.skypro.CoursePaper_2_JavaCOR.domain.Question;
import com.skypro.CoursePaper_2_JavaCOR.exceptions.IncorrectCallGetRandomQuestionBCQuestionServiceEmpty;
import com.skypro.CoursePaper_2_JavaCOR.repository.JavaQuestionRepository;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class JavaQuestionService implements QuestionService {
    private final JavaQuestionRepository javaQuestionRepository;

    public JavaQuestionService(JavaQuestionRepository javaQuestionRepository) {
        this.javaQuestionRepository = javaQuestionRepository;
    }

    @Override
    public boolean add(String question, String answer) {
        return javaQuestionRepository.add(question, answer);
    }

    @Override
    public boolean remove(Question question) {
        return javaQuestionRepository.remove(question);
    }

    @Override
    public Collection<Question> getAll() {
        return javaQuestionRepository.getAll();
    }

    @Override
    public Question getRandomQuestion() {
        // Проверяем, есть ли вопросы в репозитории
        // Если список вопросов пуст — выбрасываем специальное исключение
        if (javaQuestionRepository.getAll().isEmpty()) {
            throw new IncorrectCallGetRandomQuestionBCQuestionServiceEmpty();
        }

        // Создаём экземпляр класса Random для генерации случайного числа
        Random random = new Random();

        // Генерируем случайное число в диапазоне от 0 до размера списка вопросов (не включая верхний предел)
        int randomNumber = random.nextInt(javaQuestionRepository.getAll().size());

        // Получаем вопрос по сгенерированному случайному индексу
        return javaQuestionRepository.getQuestion(randomNumber);
    }

}
