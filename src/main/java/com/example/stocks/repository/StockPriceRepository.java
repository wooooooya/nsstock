package com.example.stocks.repository;

import com.example.stocks.entity.StockPriceEn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockPriceRepository extends JpaRepository<StockPriceEn, Long> {
}
