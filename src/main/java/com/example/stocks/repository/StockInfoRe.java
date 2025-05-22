package com.example.stocks.repository;

import com.example.stocks.entity.KospiIndexEn;
import com.example.stocks.entity.StockInfoEn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockInfoRe extends JpaRepository<StockInfoEn, String> {
    @Query(value = "SELECT * FROM stock_info WHERE short_code = :short_code", nativeQuery = true)
    Optional<StockInfoEn> findByShortCode(@Param("short_code") String shortCode);
}