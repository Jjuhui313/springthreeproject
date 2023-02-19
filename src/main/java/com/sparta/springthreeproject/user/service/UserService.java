package com.sparta.springthreeproject.user.service;

import com.sparta.springthreeproject.jwt.JwtUtil;
import com.sparta.springthreeproject.user.dto.LoginRequestDto;
import com.sparta.springthreeproject.user.dto.SignUpRequestDto;
import com.sparta.springthreeproject.user.entity.UserRoleEnum;
import com.sparta.springthreeproject.user.entity.Users;
import com.sparta.springthreeproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

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

    @Transactional(readOnly = true)
    public String login(LoginRequestDto loginDto, HttpServletResponse response) {
        String password = loginDto.getPassword();
        Optional<Users> findUser = userRepository.findByUserName(loginDto.getUserName());

        if(findUser.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
        }
        Users preUser = findUser.orElseThrow();

        if(!passwordEncoder.matches(password, preUser.getPassword())) {
            throw new IllegalArgumentException("아이디/비밀번호가 일치하지 않습니다.");
        }
//        return jwtUtil.createToken(preUser.getUserName());

//        String username = loginDto.getUserName();
//        String password = loginDto.getPassword();
//
//        Users users = userRepository.findByUserName(username).orElseThrow(
//                () -> new IllegalArgumentException("회원을 찾을 수 없습니다.")
//        );
//        if(!passwordEncoder.matches(password, users.getPassword())) {
//            throw new IllegalArgumentException("회원을 찾을 수 없습니다.");
//        }
        return jwtUtil.createToken(preUser.getUserName());
//        return response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(preUser.getUserName()));
    }

    @Transactional
    public void grantAdminRole(Long id) {
        Users users = userRepository.findById(id).orElseThrow();
        users.changeRole(UserRoleEnum.ADMIN);
    }
}
