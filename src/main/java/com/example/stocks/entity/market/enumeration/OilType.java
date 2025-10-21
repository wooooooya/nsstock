package com.example.stocks.entity.market.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OilType {
    GASOLINE("휘발유"),
    DIESEL("경유"),
    KEROSENE("등유");

    private final String label;
}