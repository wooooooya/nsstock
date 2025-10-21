package com.example.stocks.dto.home;

import com.example.stocks.entity.market.KospiIndexEn;
import lombok.Getter;

import java.math.BigDecimal;

// Kospi 차트 위젯
@Getter
public class KospiWidgetDto {
    private final BigDecimal closingPrice;
    private final BigDecimal kospiChange;
    private final BigDecimal kospiChangeRate;

    public KospiWidgetDto(KospiIndexEn kospiIndex) {
        this.closingPrice = kospiIndex.getClosingPrice();
        this.kospiChange = kospiIndex.getKospiChange();
        this.kospiChangeRate = kospiIndex.getKospiChangeRate();
    }
}