package com.example.stocks.controller;

import com.example.stocks.dto.home.TopTradingWidgetDto;
import com.example.stocks.dto.stock.StockListItemDto;
import com.example.stocks.service.StockService;
import com.example.stocks.service.WidgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class StockController {
    private final StockService stockService;
    private final WidgetService widgetService;

    @GetMapping("/kospi")
    public List<StockListItemDto> getKospiStocks() {
        return stockService.getKospiStockList();
    }

    @GetMapping("/top-trading")
    public List<TopTradingWidgetDto> getTopTradingWidget() {
        return widgetService.getTop5TradingVolume();
    }
}