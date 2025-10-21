package com.example.stocks.repository.market;

import com.example.stocks.entity.market.GoldPriceEn;
import com.example.stocks.entity.market.enumeration.GoldType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GoldPriceRe extends JpaRepository<GoldPriceEn, Integer> {

    // 가장 최근 날짜의 금 시세 1개를 타입별로 조회
    Optional<GoldPriceEn> findFirstByGoldTypeOrderByDateDesc(GoldType goldType);

    // 최근 15일간의 금 시세 목록을 타입별로 조회
    List<GoldPriceEn> findTop15ByGoldTypeOrderByDateDesc(GoldType goldType);
}