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
        // Это предотвращает некорректную работу метода при отсутствии данных
        if (javaQuestionRepository.getAll().isEmpty()) {
            throw new IncorrectCallGetRandomQuestionBCQuestionServiceEmpty();
        }

        // Создаём экземпляр класса Random для генерации случайного числа
        // Этот объект будет использоваться для выбора индекса случайного вопроса
        Random random = new Random();

        // Генерируем случайное число в диапазоне от 0 до размера списка вопросов (не включая верхний предел)
        // Метод nextInt(int bound) возвращает число от 0 (включительно) до bound (не включая)
        // Это гарантирует, что индекс будет корректным для доступа к элементам списка
        int randomNumber = random.nextInt(javaQuestionRepository.getAll().size());

        // Получаем вопрос по сгенерированному случайному индексу
        // Предполагаем, что метод getQuestion(int index) возвращает вопрос по его порядковому номеру
        // Возвращаем выбранный случайным образом вопрос
        return javaQuestionRepository.getQuestion(randomNumber);
    }

}
