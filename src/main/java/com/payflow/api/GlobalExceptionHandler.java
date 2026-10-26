package com.payflow.api;

import com.payflow.api.invoice.InvalidCurrencyCodeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidCurrencyCodeException.class)
    public ProblemDetail handleInvalidCurrency(InvalidCurrencyCodeException ex) {

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "The supplied currency code is invalid.");

        problem.setTitle("Invalid currency code");

        return problem;
    }
}
