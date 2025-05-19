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
public class MainResponseDto {
    private String code; // 응답 코드 (예: "200")
    private String message; // 응답 메시지 (예: "성공")
    private KospiRate kospiRate; // 코스피
    private ExchangeRate exchangeRate; // 환율
    private OilRate oilRate; // 유가
    private DomesticHoldings domesticHoldings; // 개인 보유량
    private ForeignHoldings foreignHoldings; // 외국인 보유량
    private InstitutionalHoldings institutionalHoldings; // 기관 보유량
    private List<TradingVolume> tradingVolume; // 거래량

    //코스피
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class KospiRate {
        private PreviousClose previousClose; // 전일 종가 정보 (가격, 증감, 증감률)
        private List<KospiChart> kospiChat; // 코스피 지수 차트 데이터 (시간별 또는 날짜별)

        //코스피 차트
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public static class KospiChart {
            private long kChat; // 시점의 코스피 지수
            private String kDate; // 해당 시점 (예: "2024-05-16")
        }
    }

    //환율
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ExchangeRate {
        private PreviousClose exchangePreviousClose; // 전일 환율 정보
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

    //유가
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class OilRate {
        private PreviousClose oilPreviousClose; // 전일 유가 정보
        private List<OilChart> oChat; // 유가 차트 데이터

         //유가 차트
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public static class OilChart {
            private long oChat; // 시점의 유가 값
            private String oDate; // 해당 시점
        }
    }

    // 개인 보유량
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class DomesticHoldings {
        private HoldingsPrevious dPreviousHoldings; // 전일 대비 개인 보유량
        private List<HoldingsChart> dChat; // 개인 보유량 차트
    }

    // 외국인 보유량
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ForeignHoldings {
        private HoldingsPrevious fPreviousHoldings; // 전일 대비 외국인 보유량
        private List<HoldingsChart> fChat; // 외국인 보유량 차트
    }

    // 기관 보유량 정보
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class InstitutionalHoldings {
        private HoldingsPrevious iPreviousHoldings; // 전일 대비 기관 보유량
        private List<HoldingsChart> iChat; // 기관 보유량 차트
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

    // 전일 종가(코스피, 환율, 유가)
    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class PreviousClose {
        private long price; // 전일 가격
        private long indecrease; // 전일 대비 증감 수치
        private double percentage; // 증감률 (%)
    }

    // 거래량 (예:상위 종목)
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TradingVolume {
        private String stocks; // 종목명
        private long volume; // 거래량
        private long volumeIndecrease; // 전일 대비 거래량 증감
        private double volumePercentage; // 거래량 증감률 (%)
    }
}