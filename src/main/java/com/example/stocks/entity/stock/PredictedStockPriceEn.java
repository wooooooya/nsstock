package com.example.stocks.entity.stock;

import com.example.stocks.entity.stock.enumeration.TargetDays;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="predicted_stock_price")
public class PredictedStockPriceEn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "predicted_id")
    private Long predictedId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "short_code", nullable = false)
    private StockInfoEn stockInfo;

    @Column(name = "prediction_date", nullable = false)
    private LocalDate predictionDate;

    @Column(name = "predicted_closing_price", nullable = false)
    private int predictedClosingPrice;

    @Column(name = "target_days", nullable = false)
    private TargetDays targetDays;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;
}