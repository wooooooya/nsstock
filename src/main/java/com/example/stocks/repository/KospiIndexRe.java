package com.example.stocks.repository;

import com.example.stocks.entity.KospiIndexEn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface KospiIndexRe extends JpaRepository<KospiIndexEn, LocalDate> {

    // 기본키를 이용하여 데이터베이스에서 조회
    @Query(value = "SELECT * FROM kospi_index WHERE date = :date", nativeQuery = true)
    Optional<KospiIndexEn> findByDate(@Param("date") LocalDate date);

    // 15일 전부터 어제까지의 종가 데이터 조회용 쿼리
    @Query(value = "SELECT * FROM kospi_index WHERE date BETWEEN :startDate AND :endDate ORDER BY date ASC", nativeQuery = true)
    List<KospiIndexEn> findAllBetweenDates(@Param("startDate") LocalDate startDate,
                                           @Param("endDate") LocalDate endDate);
}