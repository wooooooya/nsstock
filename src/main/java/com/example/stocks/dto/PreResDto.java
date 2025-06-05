package com.example.stocks.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// 전일 종가 (선택된 종목의 종가)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PreResDto {
    private String code;
    private String message;
    private PreResStockList stockList;
    private PreResStockChart stockChart;
    private PreResPrevious previousPrice;

    // 시가
    public static class OpeningPrice{
        private long openingPrice;
    }
    // 종가
    public static class ClosingClose {
        private long closingPrice; // 전일 가격
    }

    // 고가
    public static class HighestPrice{
        private long highPrice;
    }

    // 저가
    public static class LowestPrice{
        private long lowPrice;
    }

    // 예측값
    public static class PrePrice{
        private long prePrice;
        private double prepercentage;
    }
}
