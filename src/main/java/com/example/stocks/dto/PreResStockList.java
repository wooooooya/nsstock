package com.example.stocks.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PreResStockList {
    private String stockID; // 종목 ID
    private String stockName; // 종목명
    private int stockPrice; // 종목 가격
    private long volume; // 거래량
}
