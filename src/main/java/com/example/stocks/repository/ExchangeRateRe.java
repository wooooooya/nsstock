package com.example.stocks.repository;

import com.example.stocks.entity.ExchangeRateEn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExchangeRateRe extends JpaRepository<ExchangeRateEn, Integer> {

    // 기본키를 이용하여 데이터베이스에서 조회
    @Query(value = "SELECT * FROM exchange_rate WHERE currency_code = :currency_code AND date = :date", nativeQuery = true)
    Optional<ExchangeRateEn> findByCurrencyCodeANDDate(@Param("currency_code") String currencyCode, @Param("date") LocalDate date);

    // 15일 전부터 어제까지의 종가 데이터 조회용 쿼리
    @Query(value = "SELECT * FROM exchange_rate WHERE date BETWEEN :startDate AND :endDate ORDER BY date ASC", nativeQuery = true)
    List<ExchangeRateEn> findAllBetweenDates(@Param("startDate") LocalDate startDate,
                                           @Param("endDate") LocalDate endDate);
    }