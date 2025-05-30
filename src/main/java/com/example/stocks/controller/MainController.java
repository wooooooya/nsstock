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
        MaResDto kospiDto = stockServiceSer.kospiIndex();
        MaResDto exchangeDto = stockServiceSer.exchangeRate();
        MaResDto oilDto = stockServiceSer.oilPrice();
        MaResDto tradingVolumeDto = stockServiceSer.tradingVolumeTop10();

        // 각각의 데이터가 없거나 실패 코드일 경우 null 처리
        return MaResDto.builder()
                .code("SU")
                .message("Success")
                .kospiIndex(isSuccess(kospiDto) ? kospiDto.getKospiIndex() : null)
                .exchangeRate(isSuccess(exchangeDto) ? exchangeDto.getExchangeRate() : null)
                .oilPrice(isSuccess(oilDto) ? oilDto.getOilPrice() : null)
                .tradingVolume(isSuccess(tradingVolumeDto) ? tradingVolumeDto.getTradingVolume() : null)
                .build();
    }

    // 성공 여부 판단 헬퍼 메서드
    private boolean isSuccess(MaResDto dto) {
        return dto != null && "SU".equals(dto.getCode());
    }
}
