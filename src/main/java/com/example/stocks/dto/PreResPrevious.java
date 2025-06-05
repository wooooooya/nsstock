package com.example.stocks.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PreResPrevious {
    private PreResDto.PrePrice previousPrice; // 예측가
    private PreResDto.PrePrice previousPercentage; // 예측 증감률
}
