package br.com.orbitall.channels.handlers;


import br.com.orbitall.channels.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails>
    handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails (
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false),
                HttpStatus.NOT_FOUND.value(),
                null
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails>
            handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error ->{
            String fieldName = ((FieldError)error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
       ErrorDetails errorDetails = new ErrorDetails(
               LocalDateTime.now(),
               "Validation Error",
               request.getDescription(false),
               HttpStatus.BAD_REQUEST.value(),
               errors
       );
       return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception ex, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                null
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public record ErrorDetails(
            LocalDateTime timestamp,
            String message,
            String details,
            int status,
            Map<String, String> validationErrors
    ){}
}
