package com.example.stocks.repository;

import com.example.stocks.entity.StockInfoEn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockInfoRe extends JpaRepository<StockInfoEn, String> {
}