package com.example.stocks.repository;

import com.example.stocks.entity.OilPriceEn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OilPriceRe extends JpaRepository<OilPriceEn, Long> {
}