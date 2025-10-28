package com.skypro.CoursePaper_2_JavaCOR.servise;

import com.skypro.CoursePaper_2_JavaCOR.domain.Question;
import com.skypro.CoursePaper_2_JavaCOR.exceptions.IncorrectNumberOfQuestionsInTicket;
import com.skypro.CoursePaper_2_JavaCOR.service.ExaminerServiceImpl;
import com.skypro.CoursePaper_2_JavaCOR.service.JavaQuestionService;
import com.skypro.CoursePaper_2_JavaCOR.service.MathQuestionService;
import com.skypro.CoursePaper_2_JavaCOR.unit.QuestionServiceFixture;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@DisplayName("Тест сервиса - Экзаменатор")
public class ExaminerServiceImplTest {

    @Mock
    private JavaQuestionService javaQuestionService;
    @Mock
    private MathQuestionService mathQuestionService;

    @InjectMocks
    private ExaminerServiceImpl examinerService;

    @Test
    @DisplayName("Если запросить больше вопросов, чем есть, будет ошибка.")
    public void gevenAmountBiggerThenQuestionsCount_whenGetQuestions_ThenThrowException(){
        // Задаём количество запрашиваемых вопросов (заведомо больше, чем есть в сервисе)
        int amount = 10;

        Mockito.when(javaQuestionService.getAll()).thenReturn(QuestionServiceFixture.getEmptyQuestionService());

        IncorrectNumberOfQuestionsInTicket exception = Assertions.assertThrows(
                IncorrectNumberOfQuestionsInTicket.class,       // ожидаемый тип исключения
                () -> examinerService.getJavaQuestion(amount)
        );
    }

    @Test
    @DisplayName("Запросил один вопрос — получил один вопрос.")
    public void givenAmount1And1QuestionsCount_whenGetQuestions_ThenGet1Questions(){
        int amount = 1;

        Mockito.when(javaQuestionService.getAll())
                .thenReturn(QuestionServiceFixture.get1QuestionInQuestionService());

        Mockito.when(javaQuestionService.getRandomQuestion())
                .thenReturn(QuestionServiceFixture.get1Question());

        Collection<Question> result = examinerService.getJavaQuestion(amount);

        List<Question> resultList = new ArrayList<>(result);

        assertEquals(1, resultList.size());
        assertEquals("Вопрос 1", resultList.get(0).getQuestion());
    }

    @Test
    @DisplayName("Если запрошено больше вопросов, чем есть — ошибка")
    public void gevenAmountBiggerThenQuestionsCount_whenGetMathQuestions_ThenThrowException() {
        int amount = 10;

        Mockito.when(mathQuestionService.getAll())
                .thenReturn(QuestionServiceFixture.getEmptyQuestionService());

        IncorrectNumberOfQuestionsInTicket exception = Assertions.assertThrows(
                IncorrectNumberOfQuestionsInTicket.class,
                () -> examinerService.getMathQuestion(amount)
        );
    }

    @Test
    @DisplayName("При запросе 1 вопроса — получаем 1 вопрос")
    public void givenAmount1And1QuestionsCount_whenGetMathQuestions_ThenGet1Questions() {
        int amount = 1;

        Mockito.when(mathQuestionService.getAll())
                .thenReturn(QuestionServiceFixture.get1QuestionInQuestionService());

        Mockito.when(mathQuestionService.getRandomQuestion())
                .thenReturn(QuestionServiceFixture.get1Question());

        Collection<Question> result = examinerService.getMathQuestion(amount);
        List<Question> resultList = new ArrayList<>(result);

        assertEquals(1, resultList.size());
        assertEquals("Вопрос 1", resultList.get(0).getQuestion());
    }
}
