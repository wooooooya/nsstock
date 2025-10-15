package com.example.stocks.dto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

// 차트 Dto (공용)
@Getter
@AllArgsConstructor
public class ChartDataDto {
    private LocalDate date;
    private BigDecimal price;
}