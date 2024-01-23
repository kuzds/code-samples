package ru.kuzds.validation.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.kuzds.validation.dto.ErrorResponse;

import javax.validation.ConstraintViolationException;


@RestControllerAdvice
public class PayControllerAdvice {

    // is thrown due to service layer verification
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    ErrorResponse handleConstraintViolationException(ConstraintViolationException e) {
        return new ErrorResponse(e);
    }

    // is thrown due to controller layer verification
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return new ErrorResponse(e);
    }
}
