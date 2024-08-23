package com.radion.taskmanagementsystems.exception.controllers;

import com.radion.taskmanagementsystems.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(el ->
                errors.put(el.getField(), el.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(Map.of("errors", errors));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, Object>> handleResourceNotFoundException(UsernameNotFoundException ex) {
        return new ResponseEntity<>(buildResponseException(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, Object>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(buildResponseException(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, Object>> handleResourceNotFoundException(NoResourceFoundException ex) {
        return new ResponseEntity<>(buildResponseException(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, Object>> handleUserNotFoundException(UserNotFoundException ex){
        return new ResponseEntity<>(buildResponseException(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex){
        return new ResponseEntity<>(buildResponseException(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, Object>> handleHttpMessageNotReadableException(){
        return new ResponseEntity<>(buildResponseException("Invalid request data"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundTaskException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, Object>> handleNotFoundTask(NotFoundTaskException ex){
        return new ResponseEntity<>(buildResponseException(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, Object>> handleMissingServletRequestParameterException(){
        return new ResponseEntity<>(buildResponseException("Invalid request data"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadAuthenticationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<Map<String, Object>> handleBadAuthenticationException(BadAuthenticationException ex){
        return new ResponseEntity<>(buildResponseException(ex.getMessage()), HttpStatus.FORBIDDEN);
    }


    private Map<String, Object> buildResponseException(String exceptionMessage){
        return Map.of("error", exceptionMessage);
    }


}
