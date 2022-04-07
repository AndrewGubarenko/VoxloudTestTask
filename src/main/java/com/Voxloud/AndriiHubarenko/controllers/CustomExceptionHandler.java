package com.Voxloud.AndriiHubarenko.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
public class CustomExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<String> onNoSuchElementException(NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new StringBuilder()
                .append(ex.getLocalizedMessage())
                .append("\n")
                .append(ex.getStackTrace())
                .toString());
    }
    @ExceptionHandler
    public ResponseEntity<String> onRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new StringBuilder()
                .append(ex.getLocalizedMessage())
                .append("\n")
                .append(ex.getStackTrace())
                .toString());
    }
}
