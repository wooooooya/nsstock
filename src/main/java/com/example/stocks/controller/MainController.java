package com.example.stocks.controller;

import com.example.stocks.dto.MaResDto;
import com.example.stocks.service.StockServiceSer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stock")
@RequiredArgsConstructor
public class MainController {
    private final StockServiceSer stockServiceSer;

    // 코스피 메인 데이터 조회 API
    @GetMapping("/main")
    public MaResDto getMainStockInfo() {
        return stockServiceSer.kospiIndex();
    }
}
