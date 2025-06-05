package com.example.stocks.repository;

import com.example.stocks.entity.ExchangeRateEn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExchangeRateRe extends JpaRepository<ExchangeRateEn, Integer> {

    @Query(value = """
    SELECT * FROM (
        SELECT * FROM exchange_rate
        WHERE currency_code = :currencyCode
        ORDER BY date DESC
        LIMIT 15
    ) AS recent
    ORDER BY date ASC
    """, nativeQuery = true)
    List<ExchangeRateEn> findLatest15DaysByCurrency(@Param("currencyCode") String currencyCode);
}
