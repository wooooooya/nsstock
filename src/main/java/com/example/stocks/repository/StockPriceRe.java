package com.example.stocks.repository;

import com.example.stocks.entity.StockPriceEn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface StockPriceRe extends JpaRepository<StockPriceEn, Long> {
    @Query(value = "SELECT * FROM stock_price WHERE short_code = :short_code AND date = :date", nativeQuery = true)
    Optional<StockPriceEn> findByShortCodeANDDate(@Param("short_code") String shortCode, @Param("date") LocalDate date);
}