package com.example.stocks.repository;

import com.example.stocks.entity.StockPriceEn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockPriceRe extends JpaRepository<StockPriceEn, Long> {

    // 최근 날짜의 거래량 top 10
    @Query(value = """
        SELECT * FROM stock_price
        WHERE date = (SELECT MAX(date) FROM stock_price)
        ORDER BY trading_volume DESC
        LIMIT 10
        """, nativeQuery = true)
    List<StockPriceEn> findTop10ByRecentDateOrderByTradingVolumeDesc();
}

