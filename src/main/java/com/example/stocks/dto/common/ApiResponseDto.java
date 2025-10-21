package com.example.stocks.dto.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

// 상태 Dto (예 : 성공 시 200ok)
@Getter
@RequiredArgsConstructor
public class ApiResponseDto {
    private final String message;
    private final int statusCode;
}