package com.example.stocks.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//유가
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaResOilPriceDto {
    private List<OilTypeData> oilTypes; // 유종별 데이터 리스트

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class OilTypeData {
        private String oilType; // 휘발유, 경유, 등유
        private MaResDto.PreviousClose oilPreviousClose; // 전일 유가 정보
        private List<OilChart> oilChat; // 유가 차트 데이터
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class OilChart {
        private long oChat;
        private String oDate;
    }
}