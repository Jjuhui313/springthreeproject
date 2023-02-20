package com.sparta.springthreeproject.exception.advice;

import com.sparta.springthreeproject.exception.dto.ExcepMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
}
