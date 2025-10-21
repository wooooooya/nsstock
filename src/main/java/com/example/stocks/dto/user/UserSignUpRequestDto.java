package com.example.stocks.dto.user;

import lombok.Getter;
import lombok.Setter;

// 회원 가입 Dto
@Getter
@Setter
public class UserSignUpRequestDto {
    private String loginId;
    private String password;
    private String email;
}