package com.example.stocks.dto.user;

import lombok.Getter;
import lombok.Setter;

// 회원 탈퇴 Dto (ID, PW 인증)
@Getter
@Setter
public class UserWithdrawalRequestDto {
    private String loginId;
    private String password;
}
