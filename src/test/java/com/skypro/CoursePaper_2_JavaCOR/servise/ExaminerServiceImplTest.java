package com.skypro.CoursePaper_2_JavaCOR.servise;

import com.skypro.CoursePaper_2_JavaCOR.domain.Question;
import com.skypro.CoursePaper_2_JavaCOR.exceptions.IncorrectNumberOfQuestionsInTicket;
import com.skypro.CoursePaper_2_JavaCOR.service.ExaminerServiceImpl;
import com.skypro.CoursePaper_2_JavaCOR.service.QuestionService;
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
@DisplayName("Тест сервиса Экзаменатор")
public class ExaminerServiceImplTest {
    @Mock
    private QuestionService questionService;    // Создаём моковый объект сервиса вопросов — он будет имитировать поведение реального сервиса

    @InjectMocks
    private ExaminerServiceImpl examinerService; // Создаём экземпляр тестируемого сервиса, в который автоматически внедряются моки (в данном случае — questionService)

    @Test
    @DisplayName("Если запросить больше вопросов, чем есть, будет ошибка.")
    public void gevenAmountBiggerThenQuestionsCount_whenGetQuestions_ThenThrowException(){
        // Сценарий: проверяем, что при запросе количества вопросов, превышающего доступное, выбрасывается исключение

        int amount = 10; // Запрашиваем 10 вопросов

        // Настраиваем мок: при вызове questionService.getAll() возвращаем пустой список вопросов
        Mockito.when(questionService.getAll())
                .thenReturn(QuestionServiceFixture.getEmptyQuestionService());

        // Проверяем, что при вызове examinerService.getQuestion(amount) выбрасывается ожидаемое исключение
        IncorrectNumberOfQuestionsInTicket exception = Assertions.assertThrows(
                IncorrectNumberOfQuestionsInTicket.class, // Тип ожидаемого исключения
                () -> examinerService.getQuestion(amount) // Действие, которое должно вызвать исключение
        );
    }

    @Test
    @DisplayName("Запросил один вопрос — получил один вопрос.")
    public void givenAmount1And1QuestionsCount_whenGetQuestions_ThenGet1Questions(){
        // Сценарий: проверяем, что при запросе 1 вопроса и наличии 1 вопроса в системе возвращается ровно 1 вопрос

        int amount = 1; // Запрашиваем 1 вопрос

        // Настраиваем мок: при вызове questionService.getAll() возвращаем список с 1 вопросом
        Mockito.when(questionService.getAll())
                .thenReturn(QuestionServiceFixture.get1QuestionInQuestionService());

        // Настраиваем мок: при вызове questionService.getRandomQuestion() возвращаем 1 вопрос
        Mockito.when(questionService.getRandomQuestion())
                .thenReturn(QuestionServiceFixture.get1Question());

        // Вызываем метод получения вопросов
        Collection<Question> result = examinerService.getQuestion(amount);

        // Преобразуем результат в список для удобства проверки
        List<Question> resultList = new ArrayList<>(result);

        // Проверяем, что размер результата равен 1
        assertEquals(1, resultList.size());

        // Проверяем, что текст первого вопроса соответствует ожидаемому
        assertEquals("Вопрос 1", resultList.get(0).getQuestion());
    }

}
