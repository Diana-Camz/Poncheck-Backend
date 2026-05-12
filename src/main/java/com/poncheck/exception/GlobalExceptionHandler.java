package com.poncheck.exception;

import com.poncheck.dto.response.error.ErrorResponseDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    // CUSTOM EXCEPTIONS
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFound(ResourceNotFoundException exception){
        ErrorResponseDTO error = new ErrorResponseDTO(exception.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(DuplicateFieldException.class)
    public ResponseEntity<ErrorResponseDTO> handleDuplicateField(DuplicateFieldException exception){
        ErrorResponseDTO error = new ErrorResponseDTO(exception.getMessage(), HttpStatus.CONFLICT.value());
        return ResponseEntity.status(HttpStatus.CONFLICT.value()).body(error);
    }


    //GENERIC EXCEPTIONS
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleDataIntegrityViolationException(DataIntegrityViolationException exception) {
        ErrorResponseDTO error = new ErrorResponseDTO(exception.getMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        String message = exception
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("Invalid Request Data");
        ErrorResponseDTO error = new ErrorResponseDTO(message, HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDTO> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        ErrorResponseDTO error = new ErrorResponseDTO("Invalid request body. Check field names and enum values.", HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.badRequest().body(error);
    }
}
