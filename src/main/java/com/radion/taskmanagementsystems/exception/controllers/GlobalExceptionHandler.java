package com.radion.taskmanagementsystems.exception.controllers;

import com.radion.taskmanagementsystems.exception.*;
import com.radion.taskmanagementsystems.exception.model.ExceptionModel;
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
    public ResponseEntity<ExceptionModel> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(el ->
                errors.put(el.getField(), el.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(
                ExceptionModel.builder()
                        .status("Argument not valid")
                        .content(errors)
                        .build()
        );
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ExceptionModel> handleResourceNotFoundException(UsernameNotFoundException ex) {
        return new ResponseEntity<>(buildResponseException(HttpStatus.NOT_FOUND.toString(), ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ExceptionModel> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(buildResponseException(HttpStatus.NOT_FOUND.toString(), ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ExceptionModel> handleResourceNotFoundException(NoResourceFoundException ex) {
        return new ResponseEntity<>(buildResponseException(HttpStatus.NOT_FOUND.toString(), ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ExceptionModel> handleUserNotFoundException(UserNotFoundException ex){
        return new ResponseEntity<>(buildResponseException(HttpStatus.NOT_FOUND.toString(), ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionModel> handleIllegalArgumentException(IllegalArgumentException ex){
        return new ResponseEntity<>(buildResponseException(HttpStatus.BAD_REQUEST.toString(), ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionModel> handleHttpMessageNotReadableException(){
        return new ResponseEntity<>(buildResponseException(HttpStatus.BAD_REQUEST.toString(), "Invalid request data"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundTaskException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ExceptionModel> handleNotFoundTask(NotFoundTaskException ex){
        return new ResponseEntity<>(buildResponseException(HttpStatus.NOT_FOUND.toString(), ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionModel> handleMissingServletRequestParameterException(){
        return new ResponseEntity<>(buildResponseException(HttpStatus.BAD_REQUEST.toString(), "Invalid request data"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadAuthenticationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ExceptionModel> handleBadAuthenticationException(BadAuthenticationException ex){
        return new ResponseEntity<>(buildResponseException(HttpStatus.FORBIDDEN.toString(), ex.getMessage()), HttpStatus.FORBIDDEN);
    }


    private ExceptionModel buildResponseException(String status, Object content){
        return ExceptionModel.builder()
                .status(status)
                .content(
                        String.valueOf(content)
                )
                .build();
    }


}
