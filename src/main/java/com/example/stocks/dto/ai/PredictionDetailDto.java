package com.example.stocks.dto.ai;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class PredictionDetailDto {
    private LocalDate predictionDate;
    private int predictedClosingPrice;
}