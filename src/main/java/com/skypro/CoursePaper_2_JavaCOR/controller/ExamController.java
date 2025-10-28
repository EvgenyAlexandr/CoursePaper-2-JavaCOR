package com.skypro.CoursePaper_2_JavaCOR.controller;

import com.skypro.CoursePaper_2_JavaCOR.domain.Question;
import com.skypro.CoursePaper_2_JavaCOR.service.ExaminerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class ExamController {
    @Autowired
    private ExaminerServiceImpl examinerService;

    @GetMapping("/exam/get/{amount}")
    public Collection<Question> javaExam(@PathVariable("amount") int amount) {
        return examinerService.getJavaQuestion(amount);
    }

    @GetMapping("/exam/math/get/{amount}")
    public Collection<Question> mathExam(@PathVariable("amount") int amount) {
        return examinerService.getMathQuestion(amount);
    }
}