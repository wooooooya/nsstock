package com.example.stocks.entity.market;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "kospi_index")
public class KospiIndexEn {

    @Id
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "closing_price", nullable = false, precision = 7, scale = 2)
    private BigDecimal closingPrice;

    @Column(name = "kospi_change", nullable = false, precision = 5, scale = 2)
    private BigDecimal kospiChange;

    @Column(name = "kospi_change_rate", nullable = false, precision = 5, scale = 2)
    private BigDecimal kospiChangeRate;

    @Column(name = "opening_price", nullable = false, precision = 7, scale = 2)
    private BigDecimal openingPrice;

    @Column(name = "highest_price", nullable = false, precision = 7, scale = 2)
    private BigDecimal highestPrice;

    @Column(name = "lowest_price", nullable = false, precision = 7, scale = 2)
    private BigDecimal lowestPrice;

    @Column(name = "trading_volume", nullable = false)
    private int tradingVolume;

    @Column(name = "trading_value", nullable = false)
    private Long tradingValue;

    @Column(name = "employ_items_count", nullable = false)
    private int employItemsCount;

    @Column(name = "market_cap", nullable = false)
    private Long marketCap;
}