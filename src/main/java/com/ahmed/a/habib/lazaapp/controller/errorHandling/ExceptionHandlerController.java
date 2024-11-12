package com.ahmed.a.habib.lazaapp.controller.errorHandling;

import com.ahmed.a.habib.lazaapp.model.response.ExceptionResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * RestControllerAdvice for applying to all controllers
 */

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ExceptionResponse> handleBindException(BindException bindException) {
        List<String> errorsList = bindException.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        String error = String.join(", ", errorsList);
        ExceptionResponse exceptionResponse = new ExceptionResponse(error);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.FORBIDDEN);
    }
}
