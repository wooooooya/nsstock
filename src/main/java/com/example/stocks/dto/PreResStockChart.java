package com.example.stocks.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PreResStockChart {
    private PreResDto.OpeningPrice openingPrice; // 전일 시가
    private PreResDto.ClosingClose closingClose; // 전일 종가
    private PreResDto.HighestPrice highestPrice; // 전일 고가
    private PreResDto.LowestPrice lowestPrice; // 전일 저가
    private List<PreResStockChart.StockChart> stockCharts; // 종목 지수 차트 데이터 (시간별 또는 날짜별)

    //종목 차트
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class StockChart {
        private long sChat; // 시점의 종목 지수
        private String sDate; // 해당 시점
    }
}
