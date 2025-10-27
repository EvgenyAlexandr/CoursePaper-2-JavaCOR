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
        // Это предотвращает попытку получения случайного элемента из пустого набора данных
        if (mathQuestionRepository.getAll().isEmpty()) {
            throw new IncorrectCallGetRandomQuestionBCQuestionServiceEmpty();
        }

        // Создаём экземпляр класса Random для генерации псевдослучайных чисел
        // Используется стандартный генератор случайных чисел из Java API
        Random random = new Random();

        // Генерируем случайный индекс в допустимом диапазоне:
        // от 0 (включительно) до размера списка вопросов (не включая)
        // Метод nextInt(int bound) гарантирует, что полученное число
        // будет корректным индексом для доступа к элементам коллекции
        int randomNumber = random.nextInt(mathQuestionRepository.getAll().size());

        // Получаем вопрос по сгенерированному случайному индексу
        // Предполагаем, что метод getQuestion(int index) возвращает вопрос
        // из репозитория по его порядковому номеру в списке
        // Возвращаем случайно выбранный вопрос как результат метода
        return mathQuestionRepository.getQuestion(randomNumber);
    }

}
