package com.example.stocks.entity.stock.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TargetDays {
    FIVE("5"),
    TWENTY("20"),
    SIXTY("60");

    private final String value;
}