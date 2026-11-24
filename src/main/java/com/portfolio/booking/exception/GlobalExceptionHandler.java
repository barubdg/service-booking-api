package com.portfolio.booking.exception;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private Map<String, Object> buildErrorResponse
            (HttpStatus status, String message, HttpServletRequest request){

        Map<String, Object> error = new LinkedHashMap<>();

        error.put("timestamp", LocalDateTime.now());
        error.put("status",status.value());
        error.put("error",status.getReasonPhrase());
        error.put("message",message);
        error.put("path",request.getRequestURI());

        return error;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound
            (ResourceNotFoundException ex, HttpServletRequest request){

        HttpStatus status = HttpStatus.NOT_FOUND; //404 Not Found

        return ResponseEntity.status(status)
                .body(buildErrorResponse(status, ex.getMessage(), request));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String, Object>> handleBusinessException
            (BusinessException ex, HttpServletRequest request)
    {
        HttpStatus status = HttpStatus.BAD_REQUEST; //400 Bad Request

        return ResponseEntity.status(status)
                .body(buildErrorResponse(status, ex.getMessage(), request));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors
            (MethodArgumentNotValidException ex, HttpServletRequest request)
    {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() +": "+ error.getDefaultMessage())
                .distinct()
                .reduce((first, second) -> first + "; "+ second)
                .orElse("Validation failed");

        HttpStatus status = HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status)
                .body(buildErrorResponse(status, message, request));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class) // Activates when a parameter (for example in the URL) does not have the expected type
    public ResponseEntity<Map<String, Object>> handleTypeMismatch
            (MethodArgumentTypeMismatchException ex, HttpServletRequest request)
    {
        String message = "Invalid value for parameter: " + ex.getName(); // This is the name of the parameter, such as "id"

        HttpStatus status = HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status)
                .body(buildErrorResponse(status, message, request));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleUnexpectedException
            (Exception ex, HttpServletRequest request){

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // 500 Internal Server Error

        return ResponseEntity.status(status)
                .body(buildErrorResponse(status, ex.getMessage(), request));
    }
}
