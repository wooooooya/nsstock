package com.example.stocks.entity.stock;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="stock_indicator")
public class StockIndicatorEn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "indicator_id")
    private Long indicatorId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_price_id", nullable = false)
    private StockPriceEn stockPrice;

    @Column(name = "ma_5", nullable = false, precision = 14, scale = 5)
    private BigDecimal ma5;

    @Column(name = "ma_20", nullable = false, precision = 14, scale = 5)
    private BigDecimal ma20;

    @Column(name = "ma_60", nullable = false, precision = 14, scale = 5)
    private BigDecimal ma60;

    @Column(name = "rsi_7", nullable = false, precision = 7, scale = 4)
    private BigDecimal rsi7;

    @Column(name = "rsi_14", nullable = false, precision = 7, scale = 4)
    private BigDecimal rsi14;

    @Column(name = "rsi_28", nullable = false, precision = 7, scale = 4)
    private BigDecimal rsi28;

    @Column(name = "bb_upper", nullable = false, precision = 14, scale = 5)
    private BigDecimal bbUpper;

    @Column(name = "bb_mid", nullable = false, precision = 14, scale = 5)
    private BigDecimal bbMid;

    @Column(name = "bb_lower", nullable = false, precision = 14, scale = 5)
    private BigDecimal bbLower;
}