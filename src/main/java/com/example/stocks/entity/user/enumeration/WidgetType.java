package com.example.stocks.entity.user.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WidgetType {
    KOSPI_INDEX("핵심 지표", 2),
    KOSPI_CHART("코스피 차트", 2),
    TRADING_TOP5("거래량 상위", 1),
    FAVORITES("즐겨찾기", 1),
    GOLD_PRICE("금 시세", 1),
    OIL_PRICE("유가 정보", 1);

    private final String label;
    private final int cellSize;
}