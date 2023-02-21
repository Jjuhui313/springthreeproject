package com.sparta.springthreeproject.exception.advice;

import com.sparta.springthreeproject.exception.dto.ExcepMsg;
import com.sparta.springthreeproject.exception.dto.ValidExcepMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<ExcepMsg> illegalArgumentExceptionExceptionHandle(IllegalArgumentException exception) {
        ExcepMsg message = new ExcepMsg(exception.getMessage(), BAD_REQUEST.value());
        return new ResponseEntity<>(message, HttpStatus.valueOf(message.getStatus()));
    }

    @ExceptionHandler
    public ResponseEntity<ExcepMsg> illegalAccessExceptionExceptionHandle(IllegalAccessException exception) {
        ExcepMsg message = new ExcepMsg(exception.getMessage(), FORBIDDEN.value());
        return new ResponseEntity<>(message, HttpStatus.valueOf(message.getStatus()));
    }

    @ExceptionHandler
    public ResponseEntity<ValidExcepMsg> methodArgumentNotValidExceptionHandle(MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        List<String> errors = fieldErrors.stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
        ValidExcepMsg msg = new ValidExcepMsg(errors, BAD_REQUEST);
        return new ResponseEntity<>(msg, HttpStatus.valueOf(msg.getStatusCode()));

    }
}
