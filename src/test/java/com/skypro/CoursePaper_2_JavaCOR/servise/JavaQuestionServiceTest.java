package com.skypro.CoursePaper_2_JavaCOR.servise;

import com.skypro.CoursePaper_2_JavaCOR.domain.Question;
import com.skypro.CoursePaper_2_JavaCOR.exceptions.IncorrectCallGetRandomQuestionBCQuestionServiceEmpty;
import com.skypro.CoursePaper_2_JavaCOR.repository.JavaQuestionRepository;
import com.skypro.CoursePaper_2_JavaCOR.service.JavaQuestionService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Тест сервиса - Вопросы")
public class JavaQuestionServiceTest {

    @Mock
    private JavaQuestionRepository javaQuestionRepository;

    @InjectMocks
    private JavaQuestionService javaQuestionService;

    @Test
    @DisplayName("Новый вопрос сохраняется-ли в пуле вопросов")
    public void givenNewQuestions_whenAddQuestion_ThenGetNewQuestion() {
        Mockito.when(javaQuestionRepository.add("Вопрос 1", "Ответ 1")).thenReturn(true);

        assertTrue(javaQuestionService.add("Вопрос 1", "Ответ 1"));
        verify(javaQuestionRepository).add("Вопрос 1", "Ответ 1");
    }

    @Test
    @DisplayName("Добавление 2 одинаковых вопроса с разными ответами")
    public void given2QuestionsWithAnotherAnswer_whenAddQuestion_ThenGet2NewQuestions() {
        Mockito.when(javaQuestionRepository.add("Вопрос 1", "Ответ 1")).thenReturn(true);
        Mockito.when(javaQuestionRepository.add("Вопрос 1", "Ответ 2")).thenReturn(true);

        assertTrue(javaQuestionService.add("Вопрос 1", "Ответ 1"));
        assertTrue(javaQuestionService.add("Вопрос 1", "Ответ 2"));
        verify(javaQuestionRepository).add("Вопрос 1", "Ответ 1");
        verify(javaQuestionRepository).add("Вопрос 1", "Ответ 2");
    }

    @Test
    @DisplayName("Добавление одного и того же вопроса дважды")
    public void given2EqualsQuestions_whenAddQuestion_ThenGet1NewQuestionsAndFalseReturnInSecond() {
        Mockito.when(javaQuestionRepository.add("Вопрос 1", "Ответ 1"))
                .thenReturn(true)       // первый вызов — успех
                .thenReturn(false);     // второй вызов — неудача

        // Первый вызов сервиса для добавления вопроса - Ожидаем true
        assertTrue(javaQuestionService.add("Вопрос 1", "Ответ 1"));

        // Второй вызов сервиса с теми же параметрами - Ожидаем false
        assertFalse(javaQuestionService.add("Вопрос 1", "Ответ 1"));

        verify(javaQuestionRepository, Mockito.times(2)).add("Вопрос 1", "Ответ 1");
    }

    @Test
    @DisplayName("Удаление вопроса")
    public void given1Question_whenRemove_ThenReturnTrueAndEmptyList() {
        Question testQuestion = new Question("Вопрос 1", "Ответ 1");

        Mockito.when(javaQuestionRepository.remove(testQuestion)).thenReturn(true);

        assertTrue(javaQuestionService.remove(testQuestion));
        verify(javaQuestionRepository).remove(testQuestion);
    }

    @Test
    @DisplayName("Удаление несуществующего вопроса")
    public void given1Question_whenRemoveAnother_ThenReturnFalse() {
        Question testQuestion = new Question("Вопрос 1", "Ответ 1");

        Mockito.when(javaQuestionRepository.remove(testQuestion)).thenReturn(false);

        assertFalse(javaQuestionService.remove(testQuestion));
        verify(javaQuestionRepository).remove(testQuestion);
    }

    @Test
    @DisplayName("Поиск существующего вопроса")
    public void given1Question_whenGetAll_ThenReturn1Question() {
        Question testQuestion = new Question("Вопрос 1", "Ответ 1");
        Collection<Question> testCollection = new ArrayList<>();
        testCollection.add(testQuestion);

        Mockito.when(javaQuestionRepository.getAll()).thenReturn(testCollection);
        Collection<Question> resultCollection = javaQuestionService.getAll();

        assertFalse(resultCollection.isEmpty());
        assertTrue(resultCollection.contains(testQuestion));
    }

    @Test
    @DisplayName("Поиск Не существующего вопроса")
    public void givenEmptyQuestionService_whenGetAll_ThenReturnEmptyCollections() {
        Collection<Question> testCollection = javaQuestionService.getAll();

        assertTrue(testCollection.isEmpty());
    }

    @Test
    @DisplayName("Поиск случайного Не существующего вопроса")
    public void givenEmptyQuestionService_whenGetRandomQuestions_ThenThrowException() {
        IncorrectCallGetRandomQuestionBCQuestionServiceEmpty exception = Assertions.assertThrows(
                IncorrectCallGetRandomQuestionBCQuestionServiceEmpty.class,     // ожидаемый тип исключения
                () -> javaQuestionService.getRandomQuestion()
        );
    }

    @Test
    @DisplayName("Поиск случайного существующего вопроса")
    public void givenNotEmptyQuestionService_whenGetRandomQuestions_ThenReturnQuestion() {
        Question testQuestion = new Question("Вопрос 1", "Ответ 1");
        Collection<Question> testCollection = new ArrayList<>();
        testCollection.add(testQuestion);

        Mockito.when(javaQuestionRepository.getAll()).thenReturn(testCollection);
        Mockito.when(javaQuestionRepository.getQuestion(0)).thenReturn(testQuestion);
        Question resultQuestion = javaQuestionService.getRandomQuestion();

        assertFalse(resultQuestion.getQuestion().isEmpty());
        assertEquals("Вопрос 1", resultQuestion.getQuestion());
    }
}
