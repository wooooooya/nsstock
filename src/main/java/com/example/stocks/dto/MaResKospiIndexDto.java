package com.example.stocks.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//코스피
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public  class MaResKospiIndexDto {
    private MaResDto.PreviousClose kospiPreviousClose; // 전일 종가 정보 (가격, 증감, 증감률)
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