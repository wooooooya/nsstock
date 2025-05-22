package com.example.stocks.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//환율
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public  class MaResExchangeRateDto {
    private MaResDto.PreviousClose exchangePreviousClose; // 전일 환율 정보
    private List<ExchangeChart> exchangeChat; // 환율 차트 데이터

    // 환율 차트
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ExchangeChart {
        private long eChat; // 시점의 환율 값
        private String eDate; // 해당 시점
    }
}
