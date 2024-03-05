package com.osprasoft.cursomc.resources.exception;

import javax.naming.AuthenticationException;

import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.osprasoft.cursomc.services.exception.DataIntegrityException;
import com.osprasoft.cursomc.services.exception.FileException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(
        ObjectNotFoundException e, HttpServletRequest request) {
            StandardError err = new StandardError(
                HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(DataIntegrityException.class)
    public ResponseEntity<StandardError> dataIntegrity(
        DataIntegrityException e, HttpServletRequest request) {
            StandardError err = new StandardError(
                HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validation(
        MethodArgumentNotValidException e, HttpServletRequest request) {
            ValidationError err = new ValidationError(
                HttpStatus.BAD_REQUEST.value(), "Erro de validação", System.currentTimeMillis());
                for (FieldError x : e.getBindingResult().getFieldErrors()) {
                    err.addError(x.getField(), x.getDefaultMessage());
                }
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<StandardError> authorization(
        AuthenticationException e, HttpServletRequest request) {
            StandardError err = new StandardError(
                HttpStatus.FORBIDDEN.value(), e.getMessage(), System.currentTimeMillis());
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
    }

    @ExceptionHandler(FileException.class)
    public ResponseEntity<StandardError> file(
        FileException e, HttpServletRequest request) {
            StandardError err = new StandardError(
                HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(AmazonServiceException.class)
    public ResponseEntity<StandardError> amazonService(
        AmazonServiceException e, HttpServletRequest request) {
            HttpStatus code = HttpStatus.valueOf(e.getStatusCode());
            StandardError err = new StandardError(
                code.value(), e.getMessage(), System.currentTimeMillis());
                return ResponseEntity.status(code).body(err);
    }

    @ExceptionHandler(AmazonClientException.class)
    public ResponseEntity<StandardError> amazonClient(
        AmazonClientException e, HttpServletRequest request) {
            StandardError err = new StandardError(
                HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }    

    @ExceptionHandler(AmazonS3Exception.class)
    public ResponseEntity<StandardError> amazonS3(
        AmazonS3Exception e, HttpServletRequest request) {
            StandardError err = new StandardError(
                HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    } 
}
