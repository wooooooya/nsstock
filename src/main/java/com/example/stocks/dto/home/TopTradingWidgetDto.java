package com.example.stocks.dto.home;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

// 거래량 Top5 위젯
@Getter
@AllArgsConstructor
public class TopTradingWidgetDto {
    private String korStockName;
    private BigDecimal priceChangeRate;
}