package com.skypro.CoursePaper_2_JavaCOR.controller;

import com.skypro.CoursePaper_2_JavaCOR.errors.*;
import com.skypro.CoursePaper_2_JavaCOR.exceptions.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class ExamControllerAdvice {
    @ExceptionHandler
    public ResponseEntity<ExamError> incorrectNumberOfQuestionsInTicket(IncorrectNumberOfQuestionsInTicket e) {
        ExamError response = new ExamError(ErrorCode.INOQIT, "Запрошенное количество вопросов в билете: " + e.getAmount() + ", больше чем общее количество вопросов: " + e.getCountQuestions());
        return new ResponseEntity<ExamError>(response, HttpStatusCode.valueOf(400));
    }

    @ExceptionHandler(IncorrectCallGetRandomQuestionBCQuestionServiceEmpty.class)
    public ResponseEntity<ExamError> incorrectCallGetRandomQuestionBCQuestionServiceEmpty() {
        ExamError response = new ExamError(ErrorCode.ICGRQBCQSE, "Некорректный вызов getRandomQuestion т.к. QuestionServise пуст");
        return new ResponseEntity<ExamError>(response, HttpStatusCode.valueOf(500));
    }
}