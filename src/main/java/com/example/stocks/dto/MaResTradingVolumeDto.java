package com.example.stocks.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// 거래량 (예:상위 종목)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaResTradingVolumeDto {
    private String stocks; // 종목명
    private long volume; // 거래량
    private long volumeIndecrease; // 전일 대비 거래량 증감
    private double volumePercentage; // 거래량 증감률 (%)
}
