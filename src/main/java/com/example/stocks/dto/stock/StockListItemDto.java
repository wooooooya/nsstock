package com.example.stocks.dto.stock;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

// 국내 Dto
@Getter
@AllArgsConstructor
public class StockListItemDto {
    private String shortCode;
    private String korStockName;
    private int closingPrice;
    private int priceChange;
    private BigDecimal priceChangeRate;
    private Long tradingVolume;
}