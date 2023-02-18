package com.sparta.springthreeproject.user.service;

import com.sparta.springthreeproject.jwt.JwtUtil;
import com.sparta.springthreeproject.user.dto.SignUpRequestDto;
import com.sparta.springthreeproject.user.entity.Users;
import com.sparta.springthreeproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final String SUCCESS = "회원가입 성공";

    public String signUp(SignUpRequestDto signUpRequestDto) {
        if(userRepository.findByUserName(signUpRequestDto.getUserName()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 유저이름입니다.");

        }
        Users users = new Users(signUpRequestDto);
        users.encryptionPw(passwordEncoder);
        userRepository.save(users);

        return SUCCESS;
    }
}
