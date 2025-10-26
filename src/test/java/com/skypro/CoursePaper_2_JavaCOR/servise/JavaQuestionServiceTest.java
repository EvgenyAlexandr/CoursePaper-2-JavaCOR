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

    private JavaQuestionService javaQuestionService = new JavaQuestionService();

    @Test
    @DisplayName("Новый вопрос сохраняется-ли в пуле вопросов")
    public void givenNewQuestions_whenAddQuestion_ThenGetNewQuestion() {
        javaQuestionService.add("Вопрос 1", "Ответ 1");
        assertEquals("Вопрос 1", javaQuestionService.questionPull.get(0).getQuestion());
    }

    @Test
    @DisplayName("Добавление 2 одинаковых вопроса с разными ответами")
    public void given2QuestionsWithAnotherAnswer_whenAddQuestion_ThenGet2NewQuestions() {
        javaQuestionService.add("Вопрос 1", "Ответ 1");
        javaQuestionService.add("Вопрос 1", "Ответ 2");
        assertEquals(javaQuestionService.questionPull.get(0).getQuestion(), javaQuestionService.questionPull.get(1).getQuestion());
        assertEquals("Ответ 1", javaQuestionService.questionPull.get(0).getAnswer());
        assertEquals("Ответ 2", javaQuestionService.questionPull.get(1).getAnswer());
    }

    @Test
    @DisplayName("Добавление одного и того же вопроса дважды")
    public void given2EqualsQuestions_whenAddQuestion_ThenGet1NewQuestionsAndFalseReturnInSecond() {
        javaQuestionService.add("Вопрос 1", "Ответ 1");
        assertEquals("Вопрос 1", javaQuestionService.questionPull.get(0).getQuestion());
        assertFalse(javaQuestionService.add("Вопрос 1", "Ответ 1"));
    }

    @Test
    @DisplayName("Удаление вопроса")
    public void given1Question_whenRemove_ThenReturnTrueAndEmptyList() {
        Question testQuestion = new Question("Вопрос 1", "Ответ 1");
        javaQuestionService.add(testQuestion);
        assertTrue(javaQuestionService.remove(testQuestion));
        assertTrue(javaQuestionService.questionPull.isEmpty());
    }

    @Test
    @DisplayName("Удаление несуществующего вопроса")
    public void given1Question_whenRemoveAnother_ThenReturnFalse() {
        Question testQuestion = new Question("Вопрос 1", "Ответ 1");
        javaQuestionService.add(testQuestion);
        assertFalse(javaQuestionService.remove(new Question(" ", " ")));
        assertFalse(javaQuestionService.questionPull.isEmpty());
    }

    @Test
    @DisplayName("Поиск существующего вопроса")
    public void given1Question_whenGetAll_ThenReturn1Question() {
        Question testQuestion = new Question("Вопрос 1", "Ответ 1");
        javaQuestionService.add(testQuestion);
        Collection<Question> testCollection = javaQuestionService.getAll();
        assertFalse(testCollection.isEmpty());
        assertTrue(testCollection.contains(testQuestion));
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
        IncorrectCallGetRandomQuestionBCQuestionServiceEmpty exxeption = Assertions.assertThrows(
                IncorrectCallGetRandomQuestionBCQuestionServiceEmpty.class,
                () -> javaQuestionService.getRandomQuestion());
    }

    @Test
    @DisplayName("Поиск случайного существующего вопроса")
    public void givenNotEmptyQuestionService_whenGetRandomQuestions_ThenReturnQuestion() {
        javaQuestionService.add("Вопрос 1", "Ответ 1");
        Question testQuestion = javaQuestionService.getRandomQuestion();
        assertFalse(testQuestion.getQuestion().isEmpty());
        assertEquals("Вопрос 1", testQuestion.getQuestion());
    }
}
