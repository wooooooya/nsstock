package com.example.stocks.dto.home;

import com.example.stocks.entity.market.GoldPriceEn;
import lombok.Getter;

// 금 시세 위젯
@Getter
public class GoldPriceWidgetDto {
    private final int price1kg;
    private final int price100g;

    public GoldPriceWidgetDto(GoldPriceEn gold1kg, GoldPriceEn gold100g) {
        this.price1kg = (gold1kg != null) ? gold1kg.getClosingPrice() : 0;
        this.price100g = (gold100g != null) ? gold100g.getClosingPrice() : 0;
    }
}