package com.example.stocks.entity.stock.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StockType {
    COMMON_STOCK("보통주"),
    PREFERRED_STOCK("종류주권"),
    NEW_PREFERRED_STOCK("신형우선주"),
    OLD_PREFERRED_STOCK("구형우선주");

    private final String label;
}