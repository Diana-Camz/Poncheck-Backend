package com.poncheck.exception;

import com.poncheck.dto.response.error.ErrorResponseDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    // CUSTOM EXCEPTIONS
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFound(ResourceNotFoundException exception){
        ErrorResponseDTO error = new ErrorResponseDTO(
                exception.getCode(),
                exception.getMessage(),
                exception.getResource(),
                exception.getResourceId(),
                HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(DuplicateFieldException.class)
    public ResponseEntity<ErrorResponseDTO> handleDuplicateField(DuplicateFieldException exception){
        ErrorResponseDTO error = new ErrorResponseDTO(exception.getCode(), exception.getMessage(), HttpStatus.CONFLICT.value());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(SaleAlreadyCancelledException.class)
    public ResponseEntity<ErrorResponseDTO> handleSaleAlreadyCancelled(SaleAlreadyCancelledException exception){
        ErrorResponseDTO error = new ErrorResponseDTO(exception.getCode(), exception.getMessage(), HttpStatus.CONFLICT.value());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(InvalidSaleStateException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidSaleStateException(InvalidSaleStateException exception){
        ErrorResponseDTO error = new ErrorResponseDTO(exception.getCode(), exception.getMessage(), HttpStatus.CONFLICT.value());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ErrorResponseDTO> handleInsufficientStockException(InsufficientStockException exception){
        ErrorResponseDTO error = new ErrorResponseDTO(
                exception.getCode(),
                exception.getMessage(),
                exception.getResourceId(),
                HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(ResourceDisabledException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceDisabledException(ResourceDisabledException exception){
        ErrorResponseDTO error = new ErrorResponseDTO(
                exception.getMessage(),
                exception.getResourceId(),
                HttpStatus.CONFLICT.value()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(InvalidCashMovementException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidCashMovementException(InvalidCashMovementException exception){
        ErrorResponseDTO error = new ErrorResponseDTO(exception.getMessage(), HttpStatus.CONFLICT.value()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(InvalidCashRegisterException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidCashRegisterException(InvalidCashRegisterException exception){
        ErrorResponseDTO error = new ErrorResponseDTO(exception.getMessage(), HttpStatus.CONFLICT.value()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(InvalidDateRangeException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidDateRangeException(InvalidDateRangeException exception){
        ErrorResponseDTO error = new ErrorResponseDTO(exception.getMessage(), HttpStatus.CONFLICT.value()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(InvalidMovementException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidMovementException(InvalidMovementException exception){
        ErrorResponseDTO error = new ErrorResponseDTO(exception.getMessage(), HttpStatus.CONFLICT.value()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(InvalidSaleException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidSaleException(InvalidSaleException exception){
        ErrorResponseDTO error = new ErrorResponseDTO(exception.getMessage(), HttpStatus.CONFLICT.value()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(InvalidBusinessOwnerException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidBusinessOwnerException(InvalidBusinessOwnerException exception){
        ErrorResponseDTO error = new ErrorResponseDTO(exception.getMessage(), HttpStatus.CONFLICT.value()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(InvalidUserBusinessException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidUserBusinessException(InvalidUserBusinessException exception){
        ErrorResponseDTO error = new ErrorResponseDTO(exception.getMessage(), HttpStatus.CONFLICT.value()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
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

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponseDTO> handleBadCredentialsException(BadCredentialsException exception) {
        ErrorResponseDTO error = new ErrorResponseDTO("Invalid Username or Password",
                HttpStatus.UNAUTHORIZED.value());
        return ResponseEntity.badRequest().body(error);
    }
}
