package com.skypro.CoursePaper_2_JavaCOR.servise;

import com.skypro.CoursePaper_2_JavaCOR.domain.Question;
import com.skypro.CoursePaper_2_JavaCOR.exceptions.IncorrectCallGetRandomQuestionBCQuestionServiceEmpty;
import com.skypro.CoursePaper_2_JavaCOR.repository.MathQuestionRepository;
import com.skypro.CoursePaper_2_JavaCOR.service.MathQuestionService;
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
@DisplayName("Тест сервиса - Математика")
public class MathQuestionServiceTest {
    @Mock
    private MathQuestionRepository mathQuestionRepository;
    @InjectMocks
    private MathQuestionService mathQuestionService;

    @Test
    @DisplayName("Новый вопрос сохраняется-ли в пуле вопросов")
    public void givenNewQuestions_whenAddQuestion_ThenGetNewQuestion() {
        // Настраиваем мок-объект: при вызове метода add() с параметрами "Вопрос 1" и "Ответ 1"
        // репозиторий должен вернуть true (успешное добавление)
        Mockito.when(mathQuestionRepository.add("Вопрос 1", "Ответ 1")).thenReturn(true);

        // Вызываем метод сервиса, который должен добавить вопрос
        // Проверяем, что метод возвращает true (подтверждение успешного добавления)
        assertTrue(mathQuestionService.add("Вопрос 1", "Ответ 1"));

        // Проверяем, что метод add() репозитория был вызван ровно один раз
        // с ожидаемыми параметрами "Вопрос 1" и "Ответ 1"
        verify(mathQuestionRepository).add("Вопрос 1", "Ответ 1");
    }

    @Test
    @DisplayName("Добавление 2 одинаковых вопроса с разными ответами")
    public void given2QuestionsWithAnotherAnswer_whenAddQuestion_ThenGet2NewQuestions() {
        // 1. Настройка мока репозитория: задаём ожидаемое поведение для первого вызова
        // При попытке добавить вопрос "Вопрос 1" с ответом "Ответ 1" репозиторий должен вернуть true
        // Это имитирует успешное сохранение данных в хранилище
        Mockito.when(mathQuestionRepository.add("Вопрос 1", "Ответ 1")).thenReturn(true);

        // 2. Настройка мока для второго варианта ответа
        // При добавлении того же вопроса "Вопрос 1", но с ответом "Ответ 2" также ожидаем true
        // Это проверяет, что система допускает несколько вариантов ответа на один вопрос
        Mockito.when(mathQuestionRepository.add("Вопрос 1", "Ответ 2")).thenReturn(true);

        // 3. Первый вызов сервиса: пытаемся добавить вопрос с первым вариантом ответа
        // Проверяем, что сервис возвращает true — операция прошла успешно
        assertTrue(mathQuestionService.add("Вопрос 1", "Ответ 1"));


        // 4. Второй вызов сервиса: добавляем тот же вопрос, но с другим ответом
        // Снова проверяем успешность операции по возврату true
        assertTrue(mathQuestionService.add("Вопрос 1", "Ответ 2"));

        // 5. Проверка первого вызова репозитория
        // Убеждаемся, что метод add() репозитория был вызван с параметрами ("Вопрос 1", "Ответ 1")
        // Это подтверждает, что сервис корректно передал данные для первого варианта ответа
        verify(mathQuestionRepository).add("Вопрос 1", "Ответ 1");

        // 6. Проверка второго вызова репозитория
        // Проверяем, что метод add() был вызван и со вторым набором параметров ("Вопрос 1", "Ответ 2")
        // Это доказывает, что сервис обработал оба варианта ответа и делегировал их сохранение репозиторию
        verify(mathQuestionRepository).add("Вопрос 1", "Ответ 2");
    }

    @Test
    @DisplayName("Добавление одного и того же вопроса дважды")
    public void given2EqualsQuestions_whenAddQuestion_ThenGet1NewQuestionsAndFalseReturnInSecond() {
        // 1. Настройка мока репозитория для последовательных вызовов
        // При первом вызове mathQuestionRepository.add("Вопрос 1", "Ответ 1") вернётся true (успешное добавление)
        // При втором (и последующих) вызовах с теми же параметрами вернётся false (дубликат/ошибка добавления)
        Mockito.when(mathQuestionRepository.add("Вопрос 1", "Ответ 1"))
                .thenReturn(true)   // первый вызов — успех
                .thenReturn(false); // второй вызов — неудача

        // 2. Первый вызов сервиса: добавляем вопрос с ответом
        // Проверяем, что сервис возвращает true — вопрос успешно добавлен в первый раз
        assertTrue(mathQuestionService.add("Вопрос 1", "Ответ 1"));

        // 3. Второй вызов сервиса: пытаемся добавить тот же самый вопрос с тем же ответом
        // Проверяем, что сервис возвращает false — повторное добавление отклонено
        assertFalse(mathQuestionService.add("Вопрос 1", "Ответ 1"));

        // 4. Проверка количества вызовов метода репозитория
        // Убеждаемся, что метод add() был вызван ровно 2 раза с параметрами ("Вопрос 1", "Ответ 1")
        // Это подтверждает, что сервис:
        // - сначала попытался добавить вопрос (первый вызов — true)
        // - затем повторно проверил возможность добавления (второй вызов — false)
        verify(mathQuestionRepository, Mockito.times(2)).add("Вопрос 1", "Ответ 1");
    }

    @Test
    @DisplayName("Удаление вопроса")
    public void given1Question_whenRemove_ThenReturnTrue() {
        // 1. Создаём тестовый объект вопроса
        // Инициализируем экземпляр Question с параметрами "Вопрос 1" и "Ответ 1"
        // Это имитирует вопрос, который мы планируем удалить из системы
        Question testQuestion = new Question("Вопрос 1", "Ответ 1");

        // 2. Настраиваем мок-объект репозитория
        // Задаём ожидаемое поведение: при вызове метода remove() с объектом testQuestion
        // репозиторий должен вернуть true (успешное удаление)
        // Это позволяет протестировать сервис, не завися от реальной реализации репозитория
        Mockito.when(mathQuestionRepository.remove(testQuestion)).thenReturn(true);

        // 3. Вызываем метод сервиса для удаления вопроса
        // Передаём тестовый объект question в метод remove() сервиса
        // Проверяем, что метод возвращает true — подтверждение успешного удаления
        assertTrue(mathQuestionService.remove(testQuestion));


        // 4. Проверяем, что метод репозитория был вызван с правильным аргументом
        // Убеждаемся, что сервис действительно передал объект testQuestion в репозиторий
        // Это подтверждает корректную делегировку операции удаления от сервиса к репозиторию
        verify(mathQuestionRepository).remove(testQuestion);

    }

    @Test
    @DisplayName("Удаление несуществующего вопроса")
    public void given1Question_whenRemoveAnother_ThenReturnFalse() {
        // 1. Создаём тестовый объект вопроса
        // Инициализируем экземпляр Question с параметрами "Вопрос 1" и "Ответ 1"
        // Этот объект будет использоваться как эталон для проверки логики удаления
        Question testQuestion = new Question("Вопрос 1", "Ответ 1");


        // 2. Настраиваем мок-объект репозитория
        // Задаём ожидаемое поведение: при вызове метода remove() с объектом testQuestion
        // репозиторий должен вернуть false (удаление не выполнено/вопрос не найден)
        // Это имитирует ситуацию, когда система пытается удалить вопрос, который отсутствует в хранилище
        Mockito.when(mathQuestionRepository.remove(testQuestion)).thenReturn(false);

        // 3. Вызываем метод сервиса для удаления вопроса
        // Передаём тестовый объект question в метод remove() сервиса
        // Проверяем, что метод возвращает false — подтверждение неудачного удаления
        // Это означает, что сервис корректно обработал ситуацию отсутствия вопроса в системе
        assertFalse(mathQuestionService.remove(testQuestion));


        // 4. Проверяем, что метод репозитория был вызван с правильным аргументом
        // Убеждаемся, что сервис передал объект testQuestion в репозиторий
        // Это подтверждает корректную делегировку операции удаления от сервиса к репозиторию
        // Даже при неудачном удалении вызов репозитория должен состояться
        verify(mathQuestionRepository).remove(testQuestion);
    }


    @Test
    @DisplayName("Поиск существующего вопроса")
    public void given1Question_whenGetAll_ThenReturn1Question() {
        // 1. Подготовка тестовых данных
        // Создаём экземпляр вопроса с фиксированными параметрами
        Question testQuestion = new Question("Вопрос 1", "Ответ 1");

        // Создаём коллекцию для хранения вопросов и добавляем в неё тестовый вопрос
        Collection<Question> testCollection = new ArrayList<>();
        testCollection.add(testQuestion);

        // 2. Настройка мок-объекта репозитория
        // Задаём ожидаемое поведение: при вызове getAll() репозиторий вернёт testCollection
        Mockito.when(mathQuestionRepository.getAll()).thenReturn(testCollection);

        // 3. Вызов метода сервиса
        // Получаем коллекцию вопросов через метод сервиса
        Collection<Question> resultCollection = mathQuestionService.getAll();

        // 4. Проверка результатов
        // Убеждаемся, что коллекция не пуста
        assertFalse(resultCollection.isEmpty());
        // Проверяем, что в коллекции присутствует тестовый вопрос
        assertTrue(resultCollection.contains(testQuestion));
    }

    @Test
    @DisplayName("Поиск Не существующего вопроса")
    public void givenEmptyQuestionService_whenGetAll_ThenReturnEmptyCollections() {
        // 1. Вызов метода сервиса без предварительной настройки данных
        // Предполагаем, что сервис не содержит вопросов
        Collection<Question> testCollection = mathQuestionService.getAll();

        // 2. Проверка результата
        // Убеждаемся, что возвращённая коллекция пуста
        assertTrue(testCollection.isEmpty());
    }

    @Test
    @DisplayName("Поиск случайного Не существующего вопроса")
    public void givenEmptyQuestionService_whenGetRandomQuestions_ThenThrowException() {
        // 1. Проверка исключительного поведения
        // Ожидаем, что при попытке получить случайный вопрос из пустого сервиса
        // будет выброшено исключение указанного типа
        IncorrectCallGetRandomQuestionBCQuestionServiceEmpty exception = Assertions.assertThrows(
                IncorrectCallGetRandomQuestionBCQuestionServiceEmpty.class,
                () -> mathQuestionService.getRandomQuestion()  // лямбда-выражение с вызовом метода
        );

        // Примечание: здесь дополнительно можно проверить сообщение исключения или его свойства,
        // если это требуется для полноты теста
    }

    @Test
    @DisplayName("Поиск случайного существующего вопроса")
    public void givenNotEmptyQuestionService_whenGetRandomQuestions_ThenReturnQuestion() {
        // 1. Подготовка тестовых данных
        // Создаём тестовый вопрос
        Question testQuestion = new Question("Вопрос 1", "Ответ 1");

        // Формируем коллекцию с одним вопросом
        Collection<Question> testCollection = new ArrayList<>();
        testCollection.add(testQuestion);

        // 2. Настройка мок-объектов
        // При вызове getAll() репозиторий возвращает нашу тестовую коллекцию
        Mockito.when(mathQuestionRepository.getAll()).thenReturn(testCollection);
        // При запросе вопроса по индексу 0 репозиторий возвращает testQuestion
        Mockito.when(mathQuestionRepository.getQuestion(0)).thenReturn(testQuestion);

        // 3. Вызов тестируемого метода
        // Получаем случайный вопрос через сервис
        Question resultQuestion = mathQuestionService.getRandomQuestion();

        // 4. Проверка результатов
        // Убеждаемся, что текст вопроса не пуст
        assertFalse(resultQuestion.getQuestion().isEmpty());
        // Проверяем, что возвращённый вопрос соответствует ожидаемому
        assertEquals("Вопрос 1", resultQuestion.getQuestion());
    }
}
