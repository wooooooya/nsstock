package com.example.stocks.repository;

import com.example.stocks.entity.KospiIndexEn;
import com.example.stocks.entity.PredictedStockPriceEn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PredictedStockPriceRe extends JpaRepository<PredictedStockPriceEn, Long>{
//    @Query(value = "SELECT * FROM predicted_stock_price WHERE predicted_id = :predicted_id", nativeQuery = true)
//    Optional<PredictedStockPriceEn> findByPredictedId(@Param("predicted_id") Long predictedId);
}