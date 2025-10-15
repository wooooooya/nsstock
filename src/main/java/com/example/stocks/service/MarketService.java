package com.example.stocks.service;

import com.example.stocks.dto.market.MarketDataDto;
import com.example.stocks.entity.market.enumeration.GoldType;
import com.example.stocks.entity.market.enumeration.OilType;
import com.example.stocks.repository.market.GoldPriceRe;
import com.example.stocks.repository.market.OilPriceRe;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MarketService {

    private final GoldPriceRe goldPriceRepository;
    private final OilPriceRe oilPriceRepository;

    @Transactional(readOnly = true)
    public MarketDataDto getMarketData() {

        // 1. 최신 금 시세 조회
        var gold1kg = goldPriceRepository.findFirstByGoldTypeOrderByDateDesc(GoldType.GOLD_1KG).orElse(null);
        var gold100g = goldPriceRepository.findFirstByGoldTypeOrderByDateDesc(GoldType.MINI_GOLD_100G).orElse(null);

        var goldPrices = MarketDataDto.GoldPrices.builder()
                .date1kg(gold1kg != null ? gold1kg.getDate() : null)
                .price1kg(gold1kg != null ? gold1kg.getClosingPrice() : 0)
                .date100g(gold100g != null ? gold100g.getDate() : null)
                .price100g(gold100g != null ? gold100g.getClosingPrice() : 0)
                .build();

        // 2. 최신 유가 조회
        var gasoline = oilPriceRepository.findFirstByOilTypeOrderByDateDesc(OilType.GASOLINE).orElse(null);
        var diesel = oilPriceRepository.findFirstByOilTypeOrderByDateDesc(OilType.DIESEL).orElse(null);
        var kerosene = oilPriceRepository.findFirstByOilTypeOrderByDateDesc(OilType.KEROSENE).orElse(null);

        var oilPrices = MarketDataDto.OilPrices.builder()
                .date(gasoline != null ? gasoline.getDate() : null)
                .gasolinePrice(gasoline != null ? gasoline.getAveragePriceCompetition() : null)
                .dieselPrice(diesel != null ? diesel.getAveragePriceCompetition() : null)
                .kerosenePrice(kerosene != null ? kerosene.getAveragePriceCompetition() : null)
                .build();

        // 3. 금 차트 데이터 조회 (1kg 기준, 15일)
        var goldChartData = goldPriceRepository.findTop15ByGoldTypeOrderByDateDesc(GoldType.GOLD_1KG);
        Collections.reverse(goldChartData); // 날짜 오름차순으로 변경 (차트에 맞게)
        var goldChart = goldChartData.stream()
                .map(price -> MarketDataDto.ChartData.builder()
                        .date(price.getDate())
                        .price(BigDecimal.valueOf(price.getClosingPrice())) // int to BigDecimal
                        .build())
                .collect(Collectors.toList());

        // 4. 유가 차트 데이터 조회 (휘발유 기준, 15일)
        var oilChartData = oilPriceRepository.findTop15ByOilTypeOrderByDateDesc(OilType.GASOLINE);
        Collections.reverse(oilChartData); // 날짜 오름차순으로 변경
        var oilChart = oilChartData.stream()
                .map(price -> MarketDataDto.ChartData.builder()
                        .date(price.getDate())
                        .price(price.getAveragePriceCompetition())
                        .build())
                .collect(Collectors.toList());

        // 5. 최종 DTO 조합하여 반환
        return MarketDataDto.builder()
                .latestGoldPrices(goldPrices)
                .latestOilPrices(oilPrices)
                .goldChart(goldChart)
                .oilChart(oilChart)
                .build();
    }
}