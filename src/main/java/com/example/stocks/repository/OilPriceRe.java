package com.example.stocks.repository;

import com.example.stocks.entity.OilPriceEn;
import com.example.stocks.entity.enumeration.OilType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface OilPriceRe extends JpaRepository<OilPriceEn, Integer> {

    // JPQL로 변경 - enum 사용 가능
    @Query("SELECT o FROM oil_price o WHERE o.oilType = :oilType AND o.date = :date")
    Optional<OilPriceEn> findByOilTypeAndDate(@Param("oilType") OilType oilType,
                                              @Param("date") LocalDate date);

    // 15일 전부터 어제까지의 종가 데이터 조회용 쿼리
    @Query(value = "SELECT * FROM oil_price WHERE date BETWEEN :startDate AND :endDate ORDER BY date ASC", nativeQuery = true)
    List<OilPriceEn> findAllBetweenDates(@Param("startDate") LocalDate startDate,
                                             @Param("endDate") LocalDate endDate);
    }