package com.example.stocks.service;

import com.example.stocks.dto.ai.PredictionDetailDto;
import com.example.stocks.dto.ai.StockPredictionDto;
import com.example.stocks.entity.stock.PredictedStockPriceEn;
import com.example.stocks.repository.stock.PredictedStockPriceRe;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AiService {

    private final PredictedStockPriceRe predictedStockPriceRepository;

    @Transactional(readOnly = true)
    public Collection<StockPredictionDto> getAllLatestPredictions() {
        // 1. DB에서 모든 종목의 최신 예측 데이터를 가져옴
        List<PredictedStockPriceEn> allPredictions = predictedStockPriceRepository.findAllLatestPredictions();

        // 2. 종목 코드(shortCode)를 기준으로 데이터를 그룹화하고 DTO로 변환
        Map<String, StockPredictionDto> predictionsMap = allPredictions.stream()
                .collect(Collectors.groupingBy(p -> p.getStockInfo().getShortCode()))
                .values().stream()
                .map(predictions -> {
                    StockPredictionDto dto = new StockPredictionDto(
                            predictions.get(0).getStockInfo().getShortCode(),
                            predictions.get(0).getStockInfo().getKorStockName()
                    );
                    for (PredictedStockPriceEn p : predictions) {
                        PredictionDetailDto detail = new PredictionDetailDto(p.getPredictionDate(), p.getPredictedClosingPrice());
                        switch (p.getTargetDays()) {
                            case FIVE -> dto.setPrediction5d(detail);
                            case TWENTY -> dto.setPrediction20d(detail);
                            case SIXTY -> dto.setPrediction60d(detail);
                        }
                    }
                    return dto;
                })
                .collect(Collectors.toMap(StockPredictionDto::getShortCode, dto -> dto));

        return predictionsMap.values();
    }
}