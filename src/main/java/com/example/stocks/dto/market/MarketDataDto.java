package com.example.stocks.dto.market;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

// 시장 지표 Dto
@Getter
@Builder
public class MarketDataDto {

    private GoldPrices latestGoldPrices;
    private OilPrices latestOilPrices;
    private List<ChartData> goldChart;
    private List<ChartData> oilChart;

    @Getter
    @Builder
    public static class GoldPrices {
        private LocalDate date1kg;
        private int price1kg;
        private LocalDate date100g;
        private int price100g;
    }

    @Getter
    @Builder
    public static class OilPrices {
        private LocalDate date;
        private BigDecimal gasolinePrice;
        private BigDecimal dieselPrice;
        private BigDecimal kerosenePrice;
    }

    @Getter
    @Builder
    public static class ChartData {
        private LocalDate date;
        private BigDecimal price;
    }
}