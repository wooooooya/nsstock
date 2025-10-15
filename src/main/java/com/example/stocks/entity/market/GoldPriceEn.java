// 금 시세 테이블

package com.example.stocks.entity.market;

import com.example.stocks.entity.market.enumeration.GoldType;
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
@Table(name="gold_price")
public class GoldPriceEn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gold_id")
    private int goldId;

    @Column(name = "gold_short_code", nullable = false)
    private int goldShortCode;

    @Column(name = "gold_code", nullable = false, length = 15)
    private String goldCode;

    @Column(name = "gold_type", nullable = false)
    private GoldType goldType;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "closing_price", nullable = false)
    private int closingPrice;

    @Column(name = "gold_change", nullable = false)
    private int goldChange;

    @Column(name = "gold_change_rate", nullable = false, precision = 5, scale = 2)
    private BigDecimal goldChangeRate;

    @Column(name = "opening_price", nullable = false)
    private int openingPrice;

    @Column(name = "highest_price", nullable = false)
    private int highestPrice;

    @Column(name = "lowest_price", nullable = false)
    private int lowestPrice;

    @Column(name = "trading_volume", nullable = false)
    private int tradingVolume;

    @Column(name = "trading_value", nullable = false)
    private Long tradingValue;
}