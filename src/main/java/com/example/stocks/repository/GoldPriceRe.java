package com.example.stocks.repository;

import com.example.stocks.entity.GoldPriceEn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoldPriceRe extends JpaRepository<GoldPriceEn, Long> {
}