package com.example.stocks.repository;

import com.example.stocks.entity.OilPriceEn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface OilPriceRe extends JpaRepository<OilPriceEn, Integer> {
    @Query(value = "SELECT * FROM oil_price WHERE oil_type = :oil_type AND date = :date", nativeQuery = true)
    Optional<OilPriceEn> findByOilIdANDDate(@Param("oil_type") String oilType, @Param("date") LocalDate date);
}