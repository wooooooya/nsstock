package com.example.stocks.repository;

import com.example.stocks.entity.GoldPriceEn;
import com.example.stocks.entity.enumeration.GoldType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface GoldPriceRe extends JpaRepository<GoldPriceEn, Integer> {

    // 가장 최근 날짜 2개 조회
    List<GoldPriceEn> findTop2ByGoldTypeOrderByDateDesc(GoldType goldType);

    // 차트용 - 범위 내 데이터 조회 (오름차순 정렬)
    List<GoldPriceEn> findAllByGoldTypeAndDateBetweenOrderByDateAsc(
            GoldType goldType, LocalDate startDate, LocalDate endDate
    );
}
