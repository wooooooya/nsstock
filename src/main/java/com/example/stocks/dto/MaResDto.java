package com.example.stocks.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//메인 화면  Response
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaResDto {
    private String code; // 응답 코드 (예: "200")
    private String message; // 응답 메시지 (예: "성공")
    private MaResKospiRateDto kospiRate; // 코스피
    private MaResExchangeRateDto exchangeRate; // 환율
    private MaResOilRateDto oilRate; // 유가
    private MaResDomesticHoldingsDto domesticHoldings; // 개인 보유량
    private MaResForeignHoldingsDto foreignHoldings; // 외국인 보유량
    private MaResInstitutionalHoldingsDto institutionalHoldings; // 기관 보유량
    private List<MaResTradingVolumeDto> tradingVolume; // 거래량

    // 전일 종가(코스피, 환율, 유가)
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PreviousClose {
        private long price; // 전일 가격
        private long indecrease; // 전일 대비 증감 수치
        private double percentage; // 증감률 (%)
    }

    // 보유량 전일 정보 (내국인, 외국인, 기관)
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class HoldingsPrevious {
        private long total; // 총 보유량
        private long indecrease; // 전일 대비 증감 수치
        private double percentage; // 증감률 (%)
    }

    // 보유량 차트 (내국인, 외국인, 기관)
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class HoldingsChart {
        private long holdings; // 시점의 보유량
        private String date; // 해당 시점
    }
}