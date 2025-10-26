package com.skypro.CoursePaper_2_JavaCOR.servise;

import com.skypro.CoursePaper_2_JavaCOR.domain.Question;
import com.skypro.CoursePaper_2_JavaCOR.exceptions.IncorrectCallGetRandomQuestionBCQuestionServiceEmpty;
import com.skypro.CoursePaper_2_JavaCOR.service.JavaQuestionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Тест сервиса Вопросы")
public class JavaQuestionServiceTest {
    // Инициализация тестируемого сервиса
    private JavaQuestionService javaQuestionService = new JavaQuestionService();

    @Test
    @DisplayName("Новый вопрос сохраняется-ли в пуле вопросов")
    public void givenNewQuestions_whenAddQuestion_ThenGetNewQuestion() {
        // Добавляем новый вопрос с ответом
        javaQuestionService.add("Вопрос 1", "Ответ 1");
        // Проверяем, что добавленный вопрос корректно сохранился в пуле вопросов
        assertEquals("Вопрос 1", javaQuestionService.questionPull.get(0).getQuestion());
    }

    @Test
    @DisplayName("Добавление 2 одинаковых вопроса с разными ответами")
    public void given2QuestionsWithAnotherAnswer_whenAddQuestion_ThenGet2NewQuestions() {
        // Добавляем два вопроса с одинаковым текстом, но разными ответами
        javaQuestionService.add("Вопрос 1", "Ответ 1");
        javaQuestionService.add("Вопрос 1", "Ответ 2");
        // Проверяем, что оба вопроса имеют одинаковый текст
        assertEquals(javaQuestionService.questionPull.get(0).getQuestion(), javaQuestionService.questionPull.get(1).getQuestion());
        // Убеждаемся, что ответы сохранены корректно
        assertEquals("Ответ 1", javaQuestionService.questionPull.get(0).getAnswer());
        assertEquals("Ответ 2", javaQuestionService.questionPull.get(1).getAnswer());
    }

    @Test
    @DisplayName("Добавление одного и того же вопроса дважды")
    public void given2EqualsQuestions_whenAddQuestion_ThenGet1NewQuestionsAndFalseReturnInSecond() {
        // Добавляем вопрос
        javaQuestionService.add("Вопрос 1", "Ответ 1");
        // Проверяем его наличие в пуле
        assertEquals("Вопрос 1", javaQuestionService.questionPull.get(0).getQuestion());
        // Пытаемся добавить тот же самый вопрос повторно — ожидаем false (дубликат)
        assertFalse(javaQuestionService.add("Вопрос 1", "Ответ 1"));
    }

    @Test
    @DisplayName("Удаление вопроса")
    public void given1Question_whenRemove_ThenReturnTrueAndEmptyList() {
        // Создаём тестовый вопрос
        Question testQuestion = new Question("Вопрос 1", "Ответ 1");
        // Добавляем его в сервис
        javaQuestionService.add(testQuestion);
        // Удаляем вопрос — ожидаем true (успешное удаление)
        assertTrue(javaQuestionService.remove(testQuestion));
        // Проверяем, что список вопросов стал пустым
        assertTrue(javaQuestionService.questionPull.isEmpty());
    }

    @Test
    @DisplayName("Удаление несуществующего вопроса")
    public void given1Question_whenRemoveAnother_ThenReturnFalse() {
        // Создаём и добавляем тестовый вопрос
        Question testQuestion = new Question("Вопрос 1", "Ответ 1");
        javaQuestionService.add(testQuestion);
        // Пытаемся удалить другой (несуществующий) вопрос — ожидаем false
        assertFalse(javaQuestionService.remove(new Question(" ", " ")));
        // Убеждаемся, что список вопросов не стал пустым (удаление не произошло)
        assertFalse(javaQuestionService.questionPull.isEmpty());
    }

    @Test
    @DisplayName("Поиск существующего вопроса")
    public void given1Question_whenGetAll_ThenReturn1Question() {
        // Создаём и добавляем тестовый вопрос
        Question testQuestion = new Question("Вопрос 1", "Ответ 1");
        javaQuestionService.add(testQuestion);
        // Получаем все вопросы из сервиса
        Collection<Question> testCollection = javaQuestionService.getAll();
        // Проверяем, что коллекция не пуста
        assertFalse(testCollection.isEmpty());
        // Убеждаемся, что в коллекции есть добавленный вопрос
        assertTrue(testCollection.contains(testQuestion));
    }

    @Test
    @DisplayName("Поиск Не существующего вопроса")
    public void givenEmptyQuestionService_whenGetAll_ThenReturnEmptyCollections() {
        // Получаем все вопросы из пустого сервиса
        Collection<Question> testCollection = javaQuestionService.getAll();
        // Проверяем, что возвращённая коллекция пуста
        assertTrue(testCollection.isEmpty());
    }

    @Test
    @DisplayName("Поиск случайного Не существующего вопроса")
    public void givenEmptyQuestionService_whenGetRandomQuestions_ThenThrowException() {
        // Проверяем, что при попытке получить случайный вопрос из пустого сервиса
        // выбрасывается ожидаемое исключение
        IncorrectCallGetRandomQuestionBCQuestionServiceEmpty exxeption = Assertions.assertThrows(
                IncorrectCallGetRandomQuestionBCQuestionServiceEmpty.class,
                () -> javaQuestionService.getRandomQuestion());
    }

    @Test
    @DisplayName("Поиск случайного существующего вопроса")
    public void givenNotEmptyQuestionService_whenGetRandomQuestions_ThenReturnQuestion() {
        // Добавляем вопрос в сервис
        javaQuestionService.add("Вопрос 1", "Ответ 1");
        // Получаем случайный вопрос
        Question testQuestion = javaQuestionService.getRandomQuestion();
        // Проверяем, что вопрос не пустой
        assertFalse(testQuestion.getQuestion().isEmpty());
        // Убеждаемся, что текст вопроса соответствует добавленному
        assertEquals("Вопрос 1", testQuestion.getQuestion());
    }
}
