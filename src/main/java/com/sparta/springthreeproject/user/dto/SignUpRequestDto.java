package com.sparta.springthreeproject.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
@Getter
@Builder
@RequiredArgsConstructor
public class SignUpRequestDto {

    @Size(min = 4, max = 10, message = "userName을 4자에서 10자 사이로 입력해주세요.")
    @Pattern(regexp = "[0-9a-z]+", message = "userName은 숫자, 알파벳 소문자만 가능합니다.")
    private String userName;

    @Size(min = 8, max = 15, message = "password를 8자에서 15자 사이로 입력해주세요.")
    @Pattern(regexp = "[0-9a-zA-Z!@#$%^&*(),.?\":{}|<>]+", message = "password는 알파벳 대/소문자, 숫자, !@#$%^&*(),.?\":{}|<>만 가능합니다.")
    private String password;

    public SignUpRequestDto(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
