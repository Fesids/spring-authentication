package com.application.app.exceptions;

import com.application.app.models.response.BadRequestResponse;
import com.application.app.models.response.GenericResponse;
import com.application.app.models.response.InvalidDataResponse;
import com.application.app.utils.Helpers;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolationException;
import java.nio.file.AccessDeniedException;
import java.util.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    private Map<String, String> formatMessage(String message) {
        Map<String, String> result = new HashMap<>();
        result.put("message", message);

        return result;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFound(ResourceNotFoundException ex, WebRequest request) {
        BadRequestResponse response = new BadRequestResponse(formatMessage(ex.getMessage()));

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PasswordNotMatchException.class)
    public ResponseEntity<?> passwordNotMatchException(PasswordNotMatchException ex, WebRequest request) {
        BadRequestResponse response = new BadRequestResponse(formatMessage(ex.getMessage()));

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<?> fileNotFoundException(FileNotFoundException ex, WebRequest request) {
        BadRequestResponse response = new BadRequestResponse(formatMessage(ex.getMessage()));
        ex.printStackTrace();

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<?> fileStorageException(FileStorageException ex, WebRequest request) {
        BadRequestResponse response = new BadRequestResponse(formatMessage(ex.getMessage()));
        ex.printStackTrace();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(javax.validation.ConstraintViolationException.class)
    public ResponseEntity<?> constraintViolationException(ConstraintViolationException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();

        ex.getConstraintViolations().forEach(cv -> {
            String[] strings = cv.getPropertyPath().toString().split("\\.");

            errors.put(strings[strings.length - 1], cv.getMessage());
        });

        Map<String, Map<String,String>> result = new HashMap<>();
        result.put("errors", errors);

        GenericResponse response = new GenericResponse(result);

        return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        HashMap<String, List<String>> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors()
                .forEach(objectError -> {
                    String field = "";

                    if (objectError.getArguments() != null && objectError.getArguments().length >=2){
                        field = objectError.getArguments()[1].toString();
                    }

                    if(field.length() > 0) {
                        Helpers.updateErrorHashMap(errors, field, objectError.getDefaultMessage());
                    }
                });

                ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
                    Helpers.updateErrorHashMap(errors, fieldError.getField(), fieldError.getDefaultMessage());
                });

                Map<String, Map<String, List<String>>> result = new HashMap<>();
                result.put("errors", errors);

                InvalidDataResponse response = new InvalidDataResponse(result);

                return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);

    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> accessDeniedException(AccessDeniedException ex, WebRequest request){
        BadRequestResponse response = new BadRequestResponse(formatMessage(ex.getMessage()));

        return new ResponseEntity<>(response,HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> badCredentialsException(BadCredentialsException ex, WebRequest request) {
        BadRequestResponse response = new BadRequestResponse(formatMessage(ex.getMessage()));

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest request) {
        ex.printStackTrace();

        BadRequestResponse response = new BadRequestResponse(formatMessage(ex.getMessage()));

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
