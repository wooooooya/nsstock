package com.example.stocks.entity.market;

import com.example.stocks.entity.market.enumeration.OilType;
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
@Table(name="oil_price")
public class OilPriceEn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "oil_id")
    private int oilId;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "oil_type", nullable = false)
    private OilType oilType;

    @Column(name = "average_price_competition", nullable = false, precision = 7, scale = 2)
    private BigDecimal averagePriceCompetition;

    @Column(name = "average_price_consultation", nullable = false, precision = 7, scale = 2)
    private BigDecimal averagePriceConsultation;

    // Long 타입에는 length 속성을 사용하지 않으므로 삭제
    @Column(name = "trading_volume", nullable = false)
    private Long tradingVolume;

    // Long 타입에는 length 속성을 사용하지 않으므로 삭제
    @Column(name = "trading_value", nullable = false)
    private Long tradingValue;
}