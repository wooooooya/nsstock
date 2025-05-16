package com.example.stocks.repository;

import com.example.stocks.entity.ExchangeRateEn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRateRe extends JpaRepository<ExchangeRateEn, Long> {
}