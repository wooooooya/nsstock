package com.example.stocks.repository;

import com.example.stocks.entity.PredictedStockPriceEn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PredictedStockPriceRe extends JpaRepository<PredictedStockPriceEn, Long>{
}