package com.example.stocks.repository;

import com.example.stocks.entity.StockPriceEn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    // 최근 날짜 조회
    @Query(value = "SELECT MAX(date) FROM stock_price", nativeQuery = true)
    LocalDate findMaxDate();

    // 전일(최근 날짜보다 이전 날짜) 조회
    @Query(value = "SELECT MAX(date) FROM stock_price WHERE date < :date", nativeQuery = true)
    LocalDate findPreviousDate(@Param("date") LocalDate date);

    // 특정 종목, 특정 날짜 거래량 조회
    @Query(value = """
        SELECT * FROM stock_price
        WHERE short_code = :shortCode AND date = :date
        LIMIT 1
        """, nativeQuery = true)
    Optional<StockPriceEn> findByShortCodeAndDate(@Param("shortCode") String shortCode, @Param("date") LocalDate date);


}

