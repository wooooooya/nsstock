package com.example.stocks.dto.user;

import lombok.Getter;
import lombok.Setter;

// 로그인 요청 (ID, PW)
@Getter
@Setter
public class UserLoginRequestDto {
    private String loginId;
    private String password;
}