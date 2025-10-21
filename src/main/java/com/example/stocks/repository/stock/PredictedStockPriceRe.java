package com.example.stocks.repository.stock;

import com.example.stocks.entity.stock.PredictedStockPriceEn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// 예측값 리포지토리
public interface PredictedStockPriceRe extends JpaRepository<PredictedStockPriceEn, Long> {
    @Query("SELECT p FROM PredictedStockPriceEn p JOIN FETCH p.stockInfo " +
            "WHERE p.createdAt = (SELECT MAX(p2.createdAt) FROM PredictedStockPriceEn p2 WHERE p2.stockInfo.shortCode = p.stockInfo.shortCode)")
    List<PredictedStockPriceEn> findAllLatestPredictions();
}
