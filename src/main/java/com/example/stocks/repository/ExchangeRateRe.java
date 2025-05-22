package com.example.stocks.repository;

import com.example.stocks.entity.ExchangeRateEn;
import com.example.stocks.entity.OilPriceEn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ExchangeRateRe extends JpaRepository<ExchangeRateEn, Integer> {
    @Query(value = "SELECT * FROM exchange_rate WHERE currency_code = :currency_code AND date = :date", nativeQuery = true)
    Optional<OilPriceEn> findByCurrencyCodeANDDate(@Param("currency_code") String currencyCode, @Param("date") LocalDate date);
    }