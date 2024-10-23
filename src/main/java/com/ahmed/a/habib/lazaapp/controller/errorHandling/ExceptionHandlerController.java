package com.ahmed.a.habib.lazaapp.controller.errorHandling;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;

/**
 * RestControllerAdvice for applying to all controllers
 */
@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(BindException.class)
    public ResponseEntity<HashMap<String, List<String>>> handleBindException(BindException bindException) {
        List<String> errorsList = bindException.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        HashMap<String, List<String>> errorMap = new HashMap<>();
        errorMap.put("errors", errorsList);
        return new ResponseEntity<>(errorMap, HttpStatus.OK);
    }
}
