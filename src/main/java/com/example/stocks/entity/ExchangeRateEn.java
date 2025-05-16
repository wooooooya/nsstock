package com.example.stocks.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="exchange_rate")
@Table(name="exchange_rate")
public class ExchangeRateEn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int exchangeId;

    @Column(nullable = false, length = 10)
    private String currencyCode;

    @Column(nullable = false, precision = 10, scale = 4)
    private BigDecimal exchangeRate;

    @Column(nullable = false, precision = 10, scale = 4)
    private BigDecimal previousDifference;

    @Column(nullable = false, precision = 6, scale = 4)
    private BigDecimal changeRate;

    @Column(nullable = false, precision = 10, scale = 4)
    private BigDecimal openingPrice;

    @Column(nullable = false, precision = 10, scale = 4)
    private BigDecimal highestPrice;

    @Column(nullable = false, precision = 10, scale = 4)
    private BigDecimal lowestPrice;

    @Column(nullable = false, precision = 10, scale = 4)
    private BigDecimal closingPrice;
}
