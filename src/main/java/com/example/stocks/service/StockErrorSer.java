//package com.example.stocks.service;
//
//import com.example.stocks.entity.StockInfoEn;
//import com.example.stocks.exception.StockNotFoundException;
//import com.example.stocks.repository.StockInfoRe;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//@Service
//@Slf4j
//public class StockErrorSer {
//    private final StockInfoRe stockInfoRe;
//
//    public StockErrorSer(StockInfoRe stockInfoRe) {
//        this.stockInfoRe = stockInfoRe;
//    }
//    // 주식 ID에 일치하는 정보가 없을 때
//    public StockInfoEn getStockById(Long id) {
//        return (StockInfoEn) stockInfoRe.findById(id)
//                .orElseThrow(() -> {
//                    log.error("주식 정보 ID {} 를 찾을 수 없습니다.", id);
//                    return new StockNotFoundException("해당 ID의 주식 정보가 없습니다: " + id);
//                });
//    }
//}