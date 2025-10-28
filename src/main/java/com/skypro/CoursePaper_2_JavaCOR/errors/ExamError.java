package com.skypro.CoursePaper_2_JavaCOR.errors;

public class ExamError {
    private final String code;
    private final String message;

    public ExamError(int code, String message) {
        this.code = String.valueOf(code);
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}