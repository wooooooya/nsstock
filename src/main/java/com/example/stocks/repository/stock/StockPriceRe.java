package com.example.stocks.repository.stock;

import com.example.stocks.dto.stock.StockListItemDto;
import com.example.stocks.dto.home.FavoriteWidgetDto;
import com.example.stocks.dto.home.TopTradingWidgetDto;
import com.example.stocks.entity.stock.enumeration.MarketType;
import com.example.stocks.entity.stock.StockPriceEn;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockPriceRe extends JpaRepository<StockPriceEn, Long> {

    @Query("SELECT new com.example.stocks.dto.stock.StockListItemDto(" +
            "si.shortCode, " +
            "si.korStockName, " +
            "sp.closingPrice, " +
            "sp.priceChange, " +
            "sp.priceChangeRate, " +
            "sp.tradingVolume) " +
            "FROM StockPriceEn sp JOIN sp.stockInfo si " +
            "WHERE si.marketType = :marketType " +
            "AND sp.date = (SELECT MAX(sp2.date) FROM StockPriceEn sp2 WHERE sp2.stockInfo.shortCode = si.shortCode)")
    List<StockListItemDto> findLatestStocksByMarket(@Param("marketType") MarketType marketType);

    @Query("SELECT new com.example.stocks.dto.home.FavoriteWidgetDto(" +
            "si.shortCode, " +
            "si.korStockName, " +
            "sp.closingPrice) " +
            "FROM StockPriceEn sp JOIN sp.stockInfo si " +
            "WHERE si.shortCode IN :codes " + // 전달받은 종목 코드 리스트에 포함되는지 확인
            "AND sp.date = (SELECT MAX(sp2.date) FROM StockPriceEn sp2 WHERE sp2.stockInfo.shortCode = si.shortCode)")
    List<FavoriteWidgetDto> findLatestPricesByShortCodes(@Param("codes") List<String> codes);

    @Query("SELECT new com.example.stocks.dto.home.TopTradingWidgetDto(" +
            "si.korStockName, " +
            "sp.priceChangeRate) " +
            "FROM StockPriceEn sp JOIN sp.stockInfo si " +
            // 각 종목의 가장 최신 날짜의 데이터를 찾고
            "WHERE sp.date = (SELECT MAX(sp2.date) FROM StockPriceEn sp2 WHERE sp2.stockInfo.shortCode = si.shortCode) " +
            // 거래량(tradingVolume) 기준으로 내림차순 정렬
            "ORDER BY sp.tradingVolume DESC")
    // Pageable을 통해 상위 N개만 잘라옴 (여기서는 5개)
    List<TopTradingWidgetDto> findTopTradingStocks(Pageable pageable);
}