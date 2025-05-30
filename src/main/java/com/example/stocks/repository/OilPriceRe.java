package com.example.stocks.repository;

import com.example.stocks.entity.OilPriceEn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OilPriceRe extends JpaRepository<OilPriceEn, Integer> {

    @Query(value = """
    SELECT * FROM oil_price
    WHERE oil_type = :oilType
        AND date BETWEEN DATE_SUB(
            (SELECT MAX(date) FROM oil_price WHERE oil_type = :oilType), INTERVAL 15 DAY)
        AND (SELECT MAX(date) FROM oil_price WHERE oil_type = :oilType)
    ORDER BY date ASC
    """, nativeQuery = true)
    List<OilPriceEn> findLatest15DaysByOilType(@Param("oilType") String oilType);
}
