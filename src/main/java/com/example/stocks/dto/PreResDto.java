package com.example.stocks.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// 예측화면 response
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PreResDto {
    private String code;
    private String message;
}
