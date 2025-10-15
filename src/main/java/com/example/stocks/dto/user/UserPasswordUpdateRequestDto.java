package com.example.stocks.dto.user;

import lombok.Getter;
import lombok.Setter;

// PW 수정 Dto
@Getter
@Setter
public class UserPasswordUpdateRequestDto {
    private String currentPassword;
    private String newPassword;
}