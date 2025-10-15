package com.example.stocks.dto.home;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 즐겨찾기 위젯
@Getter
@AllArgsConstructor
public class FavoriteWidgetDto {
    private String shortCode;
    private String korStockName;
    private int closingPrice;
}