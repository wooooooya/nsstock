package com.example.stocks.repository;

import com.example.stocks.entity.GoldPriceEn;
import com.example.stocks.entity.enumeration.GoldType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface GoldPriceRe extends JpaRepository<GoldPriceEn, Integer> {

    // 단일 날짜 + 금 종류로 조회 (전일, 전전일 가격 조회용)
    @Query("SELECT g FROM GoldPriceEn g WHERE g.goldType = :goldType AND g.date = :date")
    Optional<GoldPriceEn> findByGoldTypeAndDate(
            @Param("goldType") GoldType goldType,
            @Param("date") LocalDate date);

    // 날짜 범위 + 금 종류로 조회 (차트용)
    @Query("SELECT g FROM GoldPriceEn g WHERE g.goldType = :goldType AND g.date BETWEEN :startDate AND :endDate ORDER BY g.date ASC")
    List<GoldPriceEn> findAllByGoldTypeAndDateBetween(
            @Param("goldType") GoldType goldType,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}