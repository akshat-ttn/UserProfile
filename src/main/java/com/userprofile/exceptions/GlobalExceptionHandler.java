package com.userprofile.exceptions;

import com.userprofile.dto.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler(NoSuchMessageException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchMessageException(NoSuchMessageException e) {
        return buildErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
        return buildErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailAlreadyInUseException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyInUse(EmailAlreadyInUseException e) {
        return buildErrorResponse(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<ErrorResponse> handlePasswordMismatch(PasswordMismatchException e) {
        return buildErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(String message, HttpStatus status) {
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), status.value(), status.getReasonPhrase(), message);
        return new ResponseEntity<>(errorResponse, status);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> responseBody = new HashMap<>();
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage())
        );

        responseBody.put(messageSource.getMessage("response.timestamp", null, LocaleContextHolder.getLocale()), LocalDateTime.now());
        responseBody.put(messageSource.getMessage("response.status", null, LocaleContextHolder.getLocale()), HttpStatus.BAD_REQUEST.value());
        responseBody.put(messageSource.getMessage("response.error", null, LocaleContextHolder.getLocale()), messageSource.getMessage("error.validation.failed", null, LocaleContextHolder.getLocale()));
        responseBody.put(messageSource.getMessage("response.message", null, LocaleContextHolder.getLocale()), errors);



        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<Map<String, Object>> handleHandlerMethodValidationException(HandlerMethodValidationException ex) {
        Map<String, Object> responseBody = new HashMap<>();
        Map<String, String> errors = new HashMap<>();

        ex.getAllErrors().forEach(error -> {
            if (error instanceof org.springframework.validation.FieldError fieldError) {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            } else if (error instanceof org.springframework.validation.ObjectError objectError) {
                errors.put(objectError.getObjectName(), objectError.getDefaultMessage());
            } else {
                errors.put("unknown", error.getDefaultMessage());
            }
        });

        responseBody.put(messageSource.getMessage("response.timestamp", null, LocaleContextHolder.getLocale()), LocalDateTime.now());
        responseBody.put(messageSource.getMessage("response.status", null, LocaleContextHolder.getLocale()), HttpStatus.BAD_REQUEST.value());
        responseBody.put(messageSource.getMessage("response.error", null, LocaleContextHolder.getLocale()), messageSource.getMessage("error.validation.failed", null, LocaleContextHolder.getLocale()));
        responseBody.put(messageSource.getMessage("response.message", null, LocaleContextHolder.getLocale()), errors);



        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }


}
