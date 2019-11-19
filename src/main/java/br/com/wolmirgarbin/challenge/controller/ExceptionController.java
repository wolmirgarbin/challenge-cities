package br.com.wolmirgarbin.challenge.controller;

import br.com.wolmirgarbin.challenge.exception.CityNotFoundException;
import br.com.wolmirgarbin.challenge.exception.UnsupportedTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = CityNotFoundException.class)
    public ResponseEntity<Object> cityNotFoundException(CityNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = UnsupportedTypeException.class)
    public ResponseEntity<Object> unsupportedTypeException(UnsupportedTypeException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
}
