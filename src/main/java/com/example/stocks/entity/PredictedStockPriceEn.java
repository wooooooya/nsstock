package com.example.stocks.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="predicted_stock_price")
@Table(name="predicted_stock_price")
public class PredictedStockPriceEn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long predictedId;

    @ManyToOne
    @JoinColumn(name = "short_code", nullable = false, columnDefinition = "varchar(15)") //길이 15, 빈칸 비 허용
    private StockInfoEn stockInfo;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private LocalDate predictionDate;

    @Column(nullable = false)
    private int predictedClosingPrice;

    @Column(nullable = false)
    private int predictedPriceChange;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal predictedPriceChangeRate;

    @Column(nullable = false)
    private int predictedOpeningPrice;

    @Column(nullable = false)
    private int predictedHighestPrice;

    @Column(nullable = false)
    private int predictedLowestPrice;

    @CreationTimestamp // 타임스탬프 기능 적용
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private LocalDateTime createAt;
}
