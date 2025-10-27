package com.skypro.CoursePaper_2_JavaCOR.servise;

import com.skypro.CoursePaper_2_JavaCOR.domain.Question;
import com.skypro.CoursePaper_2_JavaCOR.exceptions.IncorrectCallGetRandomQuestionBCQuestionServiceEmpty;
import com.skypro.CoursePaper_2_JavaCOR.repository.JavaQuestionRepository;
import com.skypro.CoursePaper_2_JavaCOR.service.JavaQuestionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Тест сервиса Вопросы")
public class JavaQuestionServiceTest {

    @Mock // Создаёт мок-объект (заглушку) для интерфейса/класса. В данном случае — для репозитория вопросов.
    private JavaQuestionRepository javaQuestionRepository;

    @InjectMocks
    // Автоматически внедряет моки (например, javaQuestionRepository) в тестируемый сервис через конструктор/сеттеры/поля.
    private JavaQuestionService javaQuestionService;

    @Test
    @DisplayName("Новый вопрос сохраняется-ли в пуле вопросов")
    public void givenNewQuestions_whenAddQuestion_ThenGetNewQuestion() {
        // Настраиваем поведение мока: при вызове метода add() с аргументами "Вопрос 1" и "Ответ 1" возвращаем true.
        Mockito.when(javaQuestionRepository.add("Вопрос 1", "Ответ 1")).thenReturn(true);

        // Вызываем метод сервиса, который должен добавить вопрос. Ожидаем true, так как мок настроен на возврат true.
        assertTrue(javaQuestionService.add("Вопрос 1", "Ответ 1"));

        // Проверяем, что метод add() репозитория был вызван ровно один раз с указанными аргументами.
        verify(javaQuestionRepository).add("Вопрос 1", "Ответ 1");
    }

    @Test
    @DisplayName("Добавление 2 одинаковых вопроса с разными ответами")
    public void given2QuestionsWithAnotherAnswer_whenAddQuestion_ThenGet2NewQuestions() {
        // Настраиваем мок-репозиторий: при попытке добавить первый вопрос с ответом "Ответ 1" возвращаем true
        Mockito.when(javaQuestionRepository.add("Вопрос 1", "Ответ 1")).thenReturn(true);

        // Настраиваем мок-репозиторий: при попытке добавить тот же вопрос, но с ответом "Ответ 2" также возвращаем true
        Mockito.when(javaQuestionRepository.add("Вопрос 1", "Ответ 2")).thenReturn(true);

        // Вызываем метод сервиса для добавления первого вопроса с ответом "Ответ 1"
        // Ожидаем, что метод вернёт true (согласно настройке мока)
        assertTrue(javaQuestionService.add("Вопрос 1", "Ответ 1"));

        // Вызываем метод сервиса для добавления того же вопроса, но с ответом "Ответ 2"
        // Ожидаем, что метод снова вернёт true (согласно второй настройке мока)
        assertTrue(javaQuestionService.add("Вопрос 1", "Ответ 2"));

        // Проверяем, что метод add() репозитория был вызван ровно один раз с аргументами "Вопрос 1", "Ответ 1"
        verify(javaQuestionRepository).add("Вопрос 1", "Ответ 1");

        // Проверяем, что метод add() репозитория был вызван ровно один раз с аргументами "Вопрос 1", "Ответ 2"
        verify(javaQuestionRepository).add("Вопрос 1", "Ответ 2");
    }

    @Test
    @DisplayName("Добавление одного и того же вопроса дважды")
    public void given2EqualsQuestions_whenAddQuestion_ThenGet1NewQuestionsAndFalseReturnInSecond() {
        // Настраиваем мок-репозиторий для метода add():
        // - Первый вызов с параметрами ("Вопрос 1", "Ответ 1") вернёт true (успешное добавление)
        // - Второй и последующие вызовы с теми же параметрами вернут false (дубликат/ошибка)
        Mockito.when(javaQuestionRepository.add("Вопрос 1", "Ответ 1"))
                .thenReturn(true)
                .thenReturn(false);

        // Первый вызов сервиса для добавления вопроса
        // Ожидаем true, так как это первое добавление (соответствует первому thenReturn(true))
        assertTrue(javaQuestionService.add("Вопрос 1", "Ответ 1"));

        // Второй вызов сервиса с теми же параметрами
        // Ожидаем false, так как мок настроен возвращать false на второй вызов (соответствует второму thenReturn(false))
        assertFalse(javaQuestionService.add("Вопрос 1", "Ответ 1"));

        // Проверяем, что метод add() репозитория был вызван ровно 2 раза с параметрами ("Вопрос 1", "Ответ 1")
        // Это подтверждает, что сервис дважды попытался добавить один и тот же вопрос
        verify(javaQuestionRepository, Mockito.times(2)).add("Вопрос 1", "Ответ 1");
    }

    @Test
    @DisplayName("Удаление вопроса")
    public void given1Question_whenRemove_ThenReturnTrueAndEmptyList() {
        // Создаём тестовый объект Question с параметрами "Вопрос 1" и "Ответ 1"
        // Этот объект будет использоваться для имитации удаления вопроса
        Question testQuestion = new Question("Вопрос 1", "Ответ 1");

        // Настраиваем мок-репозиторий: при вызове метода remove() с объектом testQuestion
        // мок должен вернуть true (имитация успешного удаления)
        Mockito.when(javaQuestionRepository.remove(testQuestion)).thenReturn(true);

        // Вызываем метод remove() сервиса с тестовым вопросом
        // Ожидаем, что метод вернёт true (согласно настройке мока)
        assertTrue(javaQuestionService.remove(testQuestion));

        // Проверяем, что метод remove() репозитория был вызван ровно один раз
        // с аргументом testQuestion (подтверждаем, что сервис делегировал вызов репозиторию)
        verify(javaQuestionRepository).remove(testQuestion);
    }

    @Test
    @DisplayName("Удаление несуществующего вопроса")
    public void given1Question_whenRemoveAnother_ThenReturnFalse() {
        // Создаём тестовый объект Question с фиксированными значениями
        // Этот вопрос будет использоваться как образец для операции удаления
        Question testQuestion = new Question("Вопрос 1", "Ответ 1");

        // Настраиваем мок-репозиторий: при вызове метода remove() с объектом testQuestion
        // мок должен вернуть false (имитация неудачного удаления — например, вопрос не найден)
        Mockito.when(javaQuestionRepository.remove(testQuestion)).thenReturn(false);

        // Вызываем метод remove() сервиса с тестовым вопросом
        // Ожидаем, что метод вернёт false (согласно настройке мока)
        assertFalse(javaQuestionService.remove(testQuestion));

        // Проверяем, что метод remove() репозитория был вызван ровно один раз
        // с аргументом testQuestion (подтверждаем, что сервис делегировал вызов репозиторию)
        verify(javaQuestionRepository).remove(testQuestion);
    }

    @Test
    @DisplayName("Поиск существующего вопроса")
    public void given1Question_whenGetAll_ThenReturn1Question() {
        // Создаём тестовый объект Question с фиксированными значениями
        // Этот вопрос будет использоваться как единственный элемент в коллекции для проверки
        Question testQuestion = new Question("Вопрос 1", "Ответ 1");

        // Создаём коллекцию (ArrayList) для хранения тестовых вопросов
        // В данном случае коллекция будет содержать только один вопрос
        Collection<Question> testCollection = new ArrayList<>();

        // Добавляем тестовый вопрос в коллекцию
        testCollection.add(testQuestion);

        // Настраиваем мок-репозиторий: при вызове метода getAll()
        // мок должен вернуть заранее подготовленную коллекцию с одним вопросом
        Mockito.when(javaQuestionRepository.getAll()).thenReturn(testCollection);

        // Вызываем метод getAll() сервиса, который должен вернуть коллекцию вопросов
        // Результат сохраняем в переменную resultCollection
        Collection<Question> resultCollection = javaQuestionService.getAll();

        // Проверяем, что возвращённая коллекция не пуста
        // Это подтверждает, что сервис получил хотя бы один вопрос от репозитория
        assertFalse(resultCollection.isEmpty());

        // Проверяем, что возвращённая коллекция содержит тестовый вопрос
        // Это гарантирует, что сервис передал корректные данные от репозитория
        assertTrue(resultCollection.contains(testQuestion));
    }

    @Test
    @DisplayName("Поиск Не существующего вопроса")
    public void givenEmptyQuestionService_whenGetAll_ThenReturnEmptyCollections() {
        // Вызываем метод getAll() сервиса, который должен вернуть коллекцию всех вопросов
        // В данном тестовом сценарии ожидается, что сервис не содержит ни одного вопроса
        // Поэтому метод должен вернуть пустую коллекцию
        Collection<Question> testCollection = javaQuestionService.getAll();


        // Проверяем, что возвращённая коллекция действительно пуста
        // Используем assertTrue с методом isEmpty(), чтобы убедиться:
        // - коллекция существует (не null);
        // - в коллекции нет ни одного элемента.
        // Это подтверждает, что сервис корректно обрабатывает случай отсутствия вопросов
        assertTrue(testCollection.isEmpty());

    }

    @Test
    @DisplayName("Поиск случайного Не существующего вопроса")
    public void givenEmptyQuestionService_whenGetRandomQuestions_ThenThrowException() {
        // Тестируем сценарий, когда сервис вопросов пуст (не содержит ни одного вопроса)
        // и пользователь пытается получить случайный вопрос через метод getRandomQuestion()

        // Ожидаем, что в такой ситуации сервис должен выбросить специальное исключение,
        // сигнализирующее о невозможности выполнить операцию
        // Тип исключения — IncorrectCallGetRandomQuestionBCQuestionServiceEmpty

        // Используем Assertions.assertThrows() — стандартный метод JUnit 5 для проверки исключений
        // Он выполняет переданную лямбду и проверяет, что она выбрасывает ожидаемое исключение
        IncorrectCallGetRandomQuestionBCQuestionServiceEmpty exception = Assertions.assertThrows(
                IncorrectCallGetRandomQuestionBCQuestionServiceEmpty.class,  // ожидаемый тип исключения
                () -> javaQuestionService.getRandomQuestion()  // действие, которое должно вызвать исключение
        );

        // На этом этапе тест успешно пройден, если:
        // 1. Метод getRandomQuestion() действительно выбросил исключение
        // 2. Тип выброшенного исключения совпадает с ожидаемым (IncorrectCallGetRandomQuestionBCQuestionServiceEmpty)
        //
        // Дополнительно можно добавить проверки свойств исключения (сообщение, причины и т.д.),
        // но в данном тесте это не требуется — достаточно факта выброса правильного типа исключения
    }

    @Test
    @DisplayName("Поиск случайного существующего вопроса")
    public void givenNotEmptyQuestionService_whenGetRandomQuestions_ThenReturnQuestion() {
        // 1. Подготовка тестовых данных: создаём вопрос, который будет использоваться в тесте
        Question testQuestion = new Question("Вопрос 1", "Ответ 1");

        // 2. Создаём коллекцию для хранения вопросов и добавляем в неё тестовый вопрос
        // Это имитирует ситуацию, когда в сервисе есть хотя бы один вопрос
        Collection<Question> testCollection = new ArrayList<>();
        testCollection.add(testQuestion);

        // 3. Настраиваем мок-объект репозитория (javaQuestionRepository)
        // Указываем, что при вызове метода getAll() он должен возвращать нашу тестовую коллекцию
        Mockito.when(javaQuestionRepository.getAll()).thenReturn(testCollection);

        // 4. Дополнительно настраиваем поведение репозитория для метода getQuestion(int index)
        // При вызове с индексом 0 репозиторий должен вернуть наш тестовый вопрос
        Mockito.when(javaQuestionRepository.getQuestion(0)).thenReturn(testQuestion);

        // 5. Вызываем тестируемый метод — получаем случайный вопрос из сервиса
        // Поскольку в коллекции только один вопрос, метод должен вернуть именно его
        Question resultQuestion = javaQuestionService.getRandomQuestion();

        // 6. Проверяем, что возвращённый вопрос не имеет пустого текста
        // Это гарантирует, что метод не вернул null или объект с пустым полем question
        assertFalse(resultQuestion.getQuestion().isEmpty());

        // 7. Проверяем, что текст вопроса совпадает с ожидаемым
        // Убеждаемся, что сервис вернул именно тот вопрос, который мы добавили в коллекцию
        assertEquals("Вопрос 1", resultQuestion.getQuestion());
    }
}
