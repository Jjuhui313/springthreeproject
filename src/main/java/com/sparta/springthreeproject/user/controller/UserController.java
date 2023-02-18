package com.sparta.springthreeproject.user.controller;

import com.sparta.springthreeproject.user.dto.AuthMessage;
import com.sparta.springthreeproject.user.dto.SignUpRequestDto;
import com.sparta.springthreeproject.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/auth/signup")
    public ResponseEntity<AuthMessage> signUp(@RequestBody @Valid SignUpRequestDto signUpRequestDto) {
        String message;
        try {
            message = userService.signUp(signUpRequestDto);
        } catch (IllegalArgumentException e) {
            AuthMessage authMessage = new AuthMessage("중복된 username입니다.", BAD_REQUEST.value());
            return new ResponseEntity<>(authMessage, BAD_REQUEST);
        }

        AuthMessage authMessage = new AuthMessage(message, OK.value());
        return new ResponseEntity<>(authMessage, OK);
    }

}
