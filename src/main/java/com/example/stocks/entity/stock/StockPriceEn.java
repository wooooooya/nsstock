package com.example.stocks.entity.stock;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

// stock_price 테이블
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="stock_price", indexes = {
        @Index(name = "idx_price_volume_value", columnList = "closing_price, price_change_rate, trading_value")
})
public class StockPriceEn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_price_id")
    private Long stockPriceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "short_code", nullable = false)
    private StockInfoEn stockInfo;

    @Column(name = "closing_price", nullable = false)
    private int closingPrice;

    @Column(name = "price_change", nullable = false)
    private int priceChange;

    @Column(name = "price_change_rate", nullable = false,  precision = 5, scale = 2)
    private BigDecimal priceChangeRate;

    @Column(name = "opening_price", nullable = false)
    private int openingPrice;

    @Column(name = "highest_price", nullable = false)
    private int highestPrice;

    @Column(name = "lowest_price", nullable = false)
    private int lowestPrice;

    @Column(name = "trading_volume", nullable = false)
    private Long tradingVolume;

    @Column(name = "trading_value", nullable = false)
    private Long tradingValue;

    @Column(name = "market_cap", nullable = false)
    private Long marketCap;

    @Column(name = "listed_stock_num", nullable = false)
    private Long listedStockNum;

    @Column(name = "date", nullable = false)
    private LocalDate date;
}