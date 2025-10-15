package com.example.stocks.dto.home;

import com.example.stocks.entity.market.OilPriceEn;
import lombok.Getter;

import java.math.BigDecimal;

// 유가 시세 위젯
@Getter
public class OilPriceWidgetDto {
    private final BigDecimal gasolinePrice;
    private final BigDecimal dieselPrice;
    private final BigDecimal kerosenePrice;

    public OilPriceWidgetDto(OilPriceEn gasoline, OilPriceEn diesel, OilPriceEn kerosene) {
        this.gasolinePrice = (gasoline != null) ? gasoline.getAveragePriceCompetition() : null;
        this.dieselPrice = (diesel != null) ? diesel.getAveragePriceCompetition() : null;
        this.kerosenePrice = (kerosene != null) ? kerosene.getAveragePriceCompetition() : null;
    }
}