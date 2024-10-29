package com.pratice.practice_sp.presentation.advice;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        // return ResponseEntity.notFound().build(); // Does not has body
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió
        // un error inesperado");
        return ResponseEntity.internalServerError().body("Ocurrió un error inesperado");
    }

}
