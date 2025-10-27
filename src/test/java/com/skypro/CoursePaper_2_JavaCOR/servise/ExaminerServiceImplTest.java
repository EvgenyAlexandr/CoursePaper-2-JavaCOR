package com.skypro.CoursePaper_2_JavaCOR.servise;

import com.skypro.CoursePaper_2_JavaCOR.domain.Question;
import com.skypro.CoursePaper_2_JavaCOR.exceptions.IncorrectNumberOfQuestionsInTicket;
import com.skypro.CoursePaper_2_JavaCOR.service.ExaminerServiceImpl;
import com.skypro.CoursePaper_2_JavaCOR.service.JavaQuestionService;
import com.skypro.CoursePaper_2_JavaCOR.service.MathQuestionService;
import com.skypro.CoursePaper_2_JavaCOR.unit.QuestionServiceFixture;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@DisplayName("Тест сервиса - Экзаменатор")
public class ExaminerServiceImplTest {

    @Mock           // Имитируем зависимость JavaQuestionService — это позволит тестировать класс без реальной реализации
    private JavaQuestionService javaQuestionService;
    @Mock           // Имитируем зависимость MathQuestionService — аналогично, для изоляции теста
    private MathQuestionService mathQuestionService;
    @InjectMocks    // Создаём экземпляр тестируемого сервиса с внедрением моков (вместо реальных зависимостей)
    private ExaminerServiceImpl examinerService;

    @Test
    @DisplayName("Если запросить больше вопросов, чем есть, будет ошибка.")
    public void gevenAmountBiggerThenQuestionsCount_whenGetQuestions_ThenThrowException(){
        // Задаём количество запрашиваемых вопросов (заведомо больше, чем есть в сервисе)
        int amount = 10;

        // Настраиваем мок: при вызове javaQuestionService.getAll() возвращаем пустой список вопросов
        // QuestionServiceFixture.getEmptyQuestionService() — вспомогательный метод для создания пустого набора данных
        Mockito.when(javaQuestionService.getAll()).thenReturn(QuestionServiceFixture.getEmptyQuestionService());

        // Проверяем, что метод examinerService.getJavaQuestion(amount) выбрасывает ожидаемое исключение
        // assertThrows() перехватывает исключение и проверяет его тип
        IncorrectNumberOfQuestionsInTicket exception = Assertions.assertThrows(
                IncorrectNumberOfQuestionsInTicket.class, // ожидаемый тип исключения
                () -> examinerService.getJavaQuestion(amount) // лямбда-выражение с вызываемым методом
        );

        // Дополнительно можно проверить сообщение исключения или другие свойства, если требуется
    }

    @Test
    @DisplayName("Запросил один вопрос — получил один вопрос.")
    public void givenAmount1And1QuestionsCount_whenGetQuestions_ThenGet1Questions(){
        // Задаём количество запрашиваемых вопросов — 1
        int amount = 1;

        // Настраиваем мок-объект javaQuestionService:
        // при вызове метода getAll() будет возвращаться список из одного вопроса,
        // подготовленный в тестовом фикстуре
        Mockito.when(javaQuestionService.getAll())
                .thenReturn(QuestionServiceFixture.get1QuestionInQuestionService());

        // при вызове метода getRandomQuestion() будет возвращаться один вопрос,
        // также подготовленный в фикстуре
        Mockito.when(javaQuestionService.getRandomQuestion())
                .thenReturn(QuestionServiceFixture.get1Question());

        // Вызываем метод getJavaQuestion() сервиса examinerService с параметром amount
        // и получаем коллекцию вопросов
        Collection<Question> result = examinerService.getJavaQuestion(amount);

        // Преобразуем полученную коллекцию в список для удобства работы с элементами
        List<Question> resultList = new ArrayList<>(result);

        // Проверяем, что в результате получен ровно один вопрос
        assertEquals(1, resultList.size());

        // Проверяем, что текст первого (и единственного) вопроса соответствует ожидаемому
        // (ожидаемый текст — «Вопрос 1»)
        assertEquals("Вопрос 1", resultList.get(0).getQuestion());
    }

    @Test
    @DisplayName("Если запрошено больше вопросов, чем есть — ошибка")
    public void gevenAmountBiggerThenQuestionsCount_whenGetMathQuestions_ThenThrowException() {
        // Задаём количество вопросов, которое запрашиваем у сервиса
        // (в данном случае — 10, что заведомо больше доступного количества)
        int amount = 10;

        // Мокируем поведение сервиса вопросов: при вызове getAll() возвращаем пустой список вопросов
        // Это имитирует ситуацию, когда в системе нет доступных математических вопросов
        Mockito.when(mathQuestionService.getAll())
                .thenReturn(QuestionServiceFixture.getEmptyQuestionService());

        // Проверяем, что при запросе 10 вопросов выбрасывается ожидаемое исключение
        // Используем assertThrows для:
        // 1. Вызова метода examinerService.getMathQuestion(amount)
        // 2. Проверки, что он бросает исключение типа IncorrectNumberOfQuestionsInTicket
        IncorrectNumberOfQuestionsInTicket exception = Assertions.assertThrows(
                IncorrectNumberOfQuestionsInTicket.class,
                () -> examinerService.getMathQuestion(amount)
        );

        // Дополнительно можно проверить сообщение исключения или другие свойства,
        // но в данном тесте это не требуется — достаточно факта выброса исключения
    }

    @Test
    @DisplayName("При запросе 1 вопроса — получаем 1 вопрос")
    public void givenAmount1And1QuestionsCount_whenGetMathQuestions_ThenGet1Questions() {
        // Задаём желаемое количество вопросов для выборки — 1
        int amount = 1;

        // Мокируем метод getAll() сервиса вопросов, чтобы он возвращал список с 1 вопросом
        // Это имитирует ситуацию, когда в системе доступен ровно 1 математический вопрос
        Mockito.when(mathQuestionService.getAll())
                .thenReturn(QuestionServiceFixture.get1QuestionInQuestionService());

        // Мокируем метод getRandomQuestion(), чтобы он возвращал конкретный вопрос
        // Это гарантирует, что при выборе случайного вопроса будет возвращён ожидаемый экземпляр
        Mockito.when(mathQuestionService.getRandomQuestion())
                .thenReturn(QuestionServiceFixture.get1Question());

        // Вызываем метод сервиса, который должен вернуть запрошенное количество вопросов
        Collection<Question> result = examinerService.getMathQuestion(amount);

        // Преобразуем коллекцию в список для удобства работы с индексами
        List<Question> resultList = new ArrayList<>(result);

        // Проверяем, что в результате действительно содержится 1 вопрос
        // Это подтверждает, что сервис корректно обработал запрос на 1 вопрос
        assertEquals(1, resultList.size());

        // Дополнительно проверяем содержание первого (и единственного) вопроса
        // Убеждаемся, что возвращён именно ожидаемый вопрос с текстом "Вопрос 1"
        assertEquals("Вопрос 1", resultList.get(0).getQuestion());
    }


}
