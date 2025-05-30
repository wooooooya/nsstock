package com.example.stocks.controller;

import com.example.stocks.dto.MaResDto;
import com.example.stocks.service.StockServiceSer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stock")
@RequiredArgsConstructor
public class MainController {
    private final StockServiceSer stockServiceSer;

    @GetMapping("/main")
    public MaResDto getMainStockInfo() {
        MaResDto kospiDto = stockServiceSer.kospiIndex(); // 코스피
        MaResDto exchangeDto = stockServiceSer.exchangeRate(); // 환율
        MaResDto oilDto = stockServiceSer.oilPrice(); // 유가

        return MaResDto.builder()
                .code("SU")
                .message("Success")
                .kospiIndex(kospiDto != null ? kospiDto.getKospiIndex() : null)
                .exchangeRate(exchangeDto != null ? exchangeDto.getExchangeRate() : null)
                .oilPrice(oilDto != null ? oilDto.getOilPrice() : null)
                .build();
    }
}
