package com.sparta.springthreeproject.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginRequestDto {
    private String userName;
    private String password;
}
