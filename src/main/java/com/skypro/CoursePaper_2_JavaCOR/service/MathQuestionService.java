package com.skypro.CoursePaper_2_JavaCOR.service;

import com.skypro.CoursePaper_2_JavaCOR.domain.Question;
import com.skypro.CoursePaper_2_JavaCOR.exceptions.IncorrectCallGetRandomQuestionBCQuestionServiceEmpty;
import com.skypro.CoursePaper_2_JavaCOR.repository.MathQuestionRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Random;

@Service
public class MathQuestionService implements QuestionService {
    private final MathQuestionRepository mathQuestionRepository;

    public MathQuestionService(MathQuestionRepository mathQuestionRepository) {
        this.mathQuestionRepository = mathQuestionRepository;
    }

    @Override
    public boolean add(String question, String answer) {
        return mathQuestionRepository.add(question, answer);
    }

    @Override
    public boolean remove(Question question) {
        return mathQuestionRepository.remove(question);
    }

    @Override
    public Collection<Question> getAll() {
        return mathQuestionRepository.getAll();
    }

    @Override
    public Question getRandomQuestion() {
        // Проверяем, содержит ли репозиторий математические вопросы
        // Если список вопросов пуст, выбрасываем специальное исключение
        if (mathQuestionRepository.getAll().isEmpty()) {
            throw new IncorrectCallGetRandomQuestionBCQuestionServiceEmpty();
        }

        // Создаём экземпляр класса Random для генерации псевдослучайных чисел
        Random random = new Random();

        // Генерируем случайный индекс в допустимом диапазоне:
        int randomNumber = random.nextInt(mathQuestionRepository.getAll().size());

        // Получаем вопрос по сгенерированному случайному индексу
        return mathQuestionRepository.getQuestion(randomNumber);
    }

}
