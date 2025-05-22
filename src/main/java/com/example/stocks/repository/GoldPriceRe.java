package com.example.stocks.repository;

import com.example.stocks.entity.GoldPriceEn;
import com.example.stocks.entity.OilPriceEn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface GoldPriceRe extends JpaRepository<GoldPriceEn, Integer> {
    @Query(value = "SELECT * FROM gold_price WHERE gold_type = :gold_type AND date = :date", nativeQuery = true)
    Optional<OilPriceEn> findByGoldIdANDDate(@Param("gold_type") String goldType, @Param("date") LocalDate date);
}