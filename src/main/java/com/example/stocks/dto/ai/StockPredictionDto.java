package com.example.stocks.dto.ai;

import com.example.stocks.entity.stock.PredictedStockPriceEn;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockPredictionDto {
    private String shortCode;
    private String korStockName;
    private PredictionDetailDto prediction5d;
    private PredictionDetailDto prediction20d;
    private PredictionDetailDto prediction60d;

    public StockPredictionDto(String shortCode, String korStockName) {
        this.shortCode = shortCode;
        this.korStockName = korStockName;
    }
}