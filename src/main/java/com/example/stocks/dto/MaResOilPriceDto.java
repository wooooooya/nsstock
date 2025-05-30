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
    private MaResDto.PreviousClose oilPreviousClose; // 전일 유가 정보
    private List<OilChart> oilChat; // 유가 차트 데이터

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
