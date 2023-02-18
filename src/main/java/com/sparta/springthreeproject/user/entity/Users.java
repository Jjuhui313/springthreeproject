package com.sparta.springthreeproject.user.entity;

import com.sparta.springthreeproject.user.dto.SignUpRequestDto;
import com.sparta.springthreeproject.util.TimeStamped;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;

import static com.sparta.springthreeproject.user.entity.UserRoleEnum.USER;

@Entity
@Getter
@NoArgsConstructor
@Validated
public class Users extends TimeStamped {
    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;

    public Users(SignUpRequestDto requestDto) {
        this.userName = requestDto.getUserName();
        this.password = requestDto.getPassword();
        this.role = USER;
    }

    public Users encryptionPw(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
        return this;
    }

//    public void changeRole(UserRoleEnum roleEnum) {
//        this.role = roleEnum;
//    }
}