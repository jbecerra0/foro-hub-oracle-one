package com.jbecerra0.ForoHub.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionConfig {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> topicoNotFound(EntityNotFoundException e) {
        return ResponseEntity.status(404).body(e.getMessage() != null ? e.getMessage() : "Recurso no encontrado.");
    }
}
