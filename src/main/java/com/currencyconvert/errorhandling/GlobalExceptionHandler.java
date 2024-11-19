package com.currencyconvert.errorhandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<Map<String, Object>> handleConstraintViolationException(ConstraintViolationException ex) {
    Map<String, Object> errors = new HashMap<>();
    errors.put("timestamp", LocalDateTime.now());
    errors.put("status", HttpStatus.BAD_REQUEST.value());
    errors.put("error", "Bad Request");

    Map<String, String> parameters = new HashMap<>();
    Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
    for (ConstraintViolation<?> violation : violations) {
      parameters.put(violation.getPropertyPath().toString(), violation.getMessage());
    }
    errors.put("parameters", parameters);

    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<Map<String, Object>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
    Map<String, Object> errors = new HashMap<>();
    errors.put("timestamp", LocalDateTime.now());
    errors.put("status", HttpStatus.BAD_REQUEST.value());
    errors.put("error", "Bad Request");
    errors.put("message", String.format("The parameter '%s' of value '%s' could not be converted to type '%s'", ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName()));
    errors.put("parameter", ex.getParameter().getParameterName());

    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<Map<String, Object>> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
    Map<String, Object> errors = new HashMap<>();
    errors.put("timestamp", LocalDateTime.now());
    errors.put("status", HttpStatus.BAD_REQUEST.value());
    errors.put("error", "Bad Request");
    errors.put("message", String.format("The required parameter '%s' is missing", ex.getParameterName()));
    errors.put("parameter", ex.getParameterName());

    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

//  @ExceptionHandler(Exception.class)
//  @ResponseStatus(HttpStatus.BAD_REQUEST)
//  public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
//    Map<String, Object> errors = new HashMap<>();
//    errors.put("timestamp", LocalDateTime.now());
//    errors.put("status", HttpStatus.BAD_REQUEST.value());
//    errors.put("error", "Bad Request");
//    errors.put("message", ex.getMessage());
//
//    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
//  }

}