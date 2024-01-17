package com.osprasoft.cursomc.resources.exception;

import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

    public ResponseEntity<StandardError> objectNotFound(
        ObjectNotFoundException e, HttpServletRequest request) {
            StandardError err = new StandardError(
                HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
        }
    
}
