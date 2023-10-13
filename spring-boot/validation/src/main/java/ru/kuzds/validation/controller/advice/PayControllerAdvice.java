package ru.kuzds.validation.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.kuzds.validation.dto.ErrorResponse;

import javax.validation.ConstraintViolationException;


@ControllerAdvice
public class PayControllerAdvice {

    // is thrown due to service layer verification
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    ErrorResponse handleConstraintValidationException(ConstraintViolationException e) {
        return new ErrorResponse(e);
    }

    // is thrown due to controller layer verification
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return new ErrorResponse(e);
    }
}
