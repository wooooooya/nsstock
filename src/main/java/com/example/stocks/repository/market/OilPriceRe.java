package com.example.stocks.repository.market;

import com.example.stocks.entity.market.OilPriceEn;
import com.example.stocks.entity.market.enumeration.OilType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OilPriceRe extends JpaRepository<OilPriceEn, Integer> {
    Optional<OilPriceEn> findFirstByOilTypeOrderByDateDesc(OilType oilType);
    List<OilPriceEn> findTop15ByOilTypeOrderByDateDesc(OilType oilType);
}