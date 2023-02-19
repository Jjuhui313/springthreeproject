package com.sparta.springthreeproject.user.controller;

import com.sparta.springthreeproject.jwt.JwtUtil;
import com.sparta.springthreeproject.security.UserDetailsImpl;
import com.sparta.springthreeproject.user.dto.AuthMessage;
import com.sparta.springthreeproject.user.dto.LoginRequestDto;
import com.sparta.springthreeproject.user.dto.SignUpRequestDto;
import com.sparta.springthreeproject.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
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

    @PostMapping("/auth/login")
    public ResponseEntity<AuthMessage> login(@RequestBody LoginRequestDto loginDto, HttpServletResponse response) {
        String userToken;

        try {
            userToken = userService.login(loginDto, response);
        }
        catch(IllegalArgumentException e) {
            AuthMessage authMessage = new AuthMessage("회원을 찾을 수 없습니다.", BAD_REQUEST.value());
            return new ResponseEntity<>(authMessage, BAD_REQUEST);
        }
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, userToken);

        AuthMessage authMessage = new AuthMessage("로그인 성공", OK.value());
        return new ResponseEntity<>(authMessage, OK);

//        userService.login(loginDto, response);
//
//        AuthMessage authMessage = new AuthMessage("로그인 성공", OK.value());
//        return new ResponseEntity<>(authMessage, OK);
    }

    @GetMapping("/auth/admin")
    public ResponseEntity<Object> adminRole(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long id = userDetails.getUser().getId();
        userService.grantAdminRole(id);
        AuthMessage authMessage = new AuthMessage("관리자로 등록되었습니다.", OK.value());

        return new ResponseEntity<>(authMessage, OK);
    }

}
