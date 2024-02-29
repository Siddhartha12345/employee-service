package com.employee.exception;

import com.employee.response.BusinessErrorResponse;
import com.employee.response.ValidationError;
import com.employee.response.ValidationErrorResponse;
import feign.FeignException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<BusinessErrorResponse> handleResourceNotFoundException(ResourceNotFoundException exception) {
        BusinessErrorResponse errorResponse = BusinessErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<BusinessErrorResponse> handleFeignExceptionNotFound(FeignException exception) {
        BusinessErrorResponse errorResponse = BusinessErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationException(MethodArgumentNotValidException exception) {
        ValidationErrorResponse errorResponse = new ValidationErrorResponse();
        List<ValidationError> validationErrorList = new ArrayList<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            ValidationError validationError = ValidationError.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message(error.getDefaultMessage())
                    .fieldName(((FieldError) error).getField())
                    .build();
            validationErrorList.add(validationError);
        });
        errorResponse.setValidationErrors(validationErrorList);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ValidationErrorResponse> handleConstraintException(ConstraintViolationException exception) {
        ValidationErrorResponse errorResponse = new ValidationErrorResponse();
        List<ValidationError> validationErrorList = new ArrayList<>();
        for(ConstraintViolation violation : exception.getConstraintViolations()) {
            ValidationError validationError = new ValidationError(HttpStatus.BAD_REQUEST, violation.getPropertyPath().toString(),
                                                                    violation.getMessageTemplate());
            validationErrorList.add(validationError);
        }
        errorResponse.setValidationErrors(validationErrorList);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
