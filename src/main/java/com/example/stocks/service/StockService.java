package com.example.stocks.service;

import com.example.stocks.dto.stock.StockListItemDto;
import com.example.stocks.entity.stock.enumeration.MarketType;
import com.example.stocks.repository.stock.StockPriceRe;
import com.example.stocks.repository.user.UserFavoriteRe;
import com.example.stocks.repository.user.UserInfoRe;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockPriceRe stockPriceRepository;
    private final UserInfoRe userInfoRepository;
    private final UserFavoriteRe userFavoriteRepository;

    @Transactional(readOnly = true)
    public List<StockListItemDto> getKospiStockList() {
        return stockPriceRepository.findLatestStocksByMarket(MarketType.KOSPI);
    }
}