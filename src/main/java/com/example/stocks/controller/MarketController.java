package com.example.stocks.controller;

import com.example.stocks.dto.common.ChartDataDto;
import com.example.stocks.dto.home.GoldPriceWidgetDto;
import com.example.stocks.dto.home.KospiWidgetDto;
import com.example.stocks.dto.home.OilPriceWidgetDto;
import com.example.stocks.dto.market.MarketDataDto;
import com.example.stocks.service.MarketService;
import com.example.stocks.service.WidgetService; // ❗ WidgetService 의존성 추가
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 금, 유가, 코스피 지수 등 시장 전체 데이터와 관련된 API를 담당합니다.
 */
@RestController
@RequestMapping("/api/market")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MarketController {

    private final MarketService marketService;
    private final WidgetService widgetService;

    @GetMapping("/summary")
    public MarketDataDto getMarketSummary() {
        return marketService.getMarketData();
    }

    @GetMapping("/kospi-chart")
    public List<ChartDataDto> getKospiChart(@RequestParam(defaultValue = "1y") String period) {
        return widgetService.getKospiChartData(period);
    }

    @GetMapping("/kospi-index")
    public KospiWidgetDto getKospiIndex() {
        return widgetService.getLatestKospiIndex();
    }

    @GetMapping("/gold-price")
    public GoldPriceWidgetDto getGoldPrices() {
        return widgetService.getLatestGoldPrices();
    }

    @GetMapping("/oil-price")
    public OilPriceWidgetDto getOilPrices() {
        return widgetService.getLatestOilPrices();
    }
}