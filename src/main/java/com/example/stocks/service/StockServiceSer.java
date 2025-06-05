package com.example.stocks.service;

import com.example.stocks.dto.*;
import com.example.stocks.entity.*;
import com.example.stocks.entity.enumeration.GoldType;
import com.example.stocks.entity.enumeration.OilType;
import com.example.stocks.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StockServiceSer {
    private final KospiIndexRe kospiIndexRe; // 코스피
    private final ExchangeRateRe exchangePriceRe; // 환율
    private final OilPriceRe oilPriceRe; // 유가
    private final StockPriceRe stockPriceRe; // 거래량
    private List<MaResTradingVolumeDto> tradingVolumeList;

    public MaResDto kospiIndex() {

        // 15일치 차트 데이터를 최신 날짜 기준으로 조회 (쿼리 내부에서 날짜 계산)
        List<KospiIndexEn> indexList = kospiIndexRe.findLatest15Days();

        // 날짜 오름차순 정렬
        indexList.sort(Comparator.comparing(KospiIndexEn::getDate));

        // 차트용 리스트 생성
        List<MaResKospiIndexDto.KospiChart> kospiChartList = indexList.stream()
                .map(item -> MaResKospiIndexDto.KospiChart.builder()
                        .kChat(item.getClosingPrice().longValue())
                        .kDate(item.getDate().toString())
                        .build())
                .toList();

        // 최신 날짜에서 어제, 그저께 날짜를 계산
        if (indexList.size() < 3) {
            return null; // 데이터가 부족하면 null 반환
        }

        KospiIndexEn latest = indexList.get(indexList.size() - 1);         // 최신
        KospiIndexEn yesterday = indexList.get(indexList.size() - 2);      // 어제
        KospiIndexEn dayBefore = indexList.get(indexList.size() - 3);      // 그저께

        BigDecimal yesterdayPrice = yesterday.getClosingPrice();
        BigDecimal dayBeforePrice = dayBefore.getClosingPrice();

        // 증감 계산
        BigDecimal diff = yesterdayPrice.subtract(dayBeforePrice);
        String indecreaseFormatted = (diff.signum() >= 0 ? "+" : "") + diff.longValue();

        // 증감률 계산
        double percentage = dayBeforePrice.compareTo(BigDecimal.ZERO) == 0
                ? 0.0
                : diff.divide(dayBeforePrice, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100))
                .doubleValue();

        String percentageFormatted = String.format("%+.2f", percentage);

        // 전일 종가 정보 생성
        MaResDto.PreviousClose previousClose = MaResDto.PreviousClose.builder()
                .price(yesterdayPrice.longValue())
                .indecrease(Long.parseLong(indecreaseFormatted))
                .percentage(Double.parseDouble(percentageFormatted))
                .build();

        // Kospi DTO 생성
        MaResKospiIndexDto kospiDto = MaResKospiIndexDto.builder()
                .kospiPreviousClose(previousClose)
                .kospiChat(kospiChartList)
                .build();

        // 최종 응답 DTO 생성
        return MaResDto.builder()
                .code("SU")
                .message("Success")
                .kospiIndex(kospiDto)
                .build();
    }

    // 환율 (USD 기준, KOSPI와 동일한 구조)
    public MaResDto exchangeRate() {
        String currency = "미국 달러 (USD)";

        // USD 기준 최근 15개 환율 데이터 조회 (최신순 -> 정렬 필요)
        List<ExchangeRateEn> rateList = exchangePriceRe.findLatest15DaysByCurrency(currency);

        if (rateList == null || rateList.size() < 2) {
            return MaResDto.builder()
                    .code("ER")
                    .message("Not enough data for exchange rate")
                    .build();
        }

        // 날짜 오름차순 정렬 (차트용)
        rateList.sort(Comparator.comparing(ExchangeRateEn::getDate));

        // 차트 데이터 구성
        List<MaResExchangeRateDto.ExchangeChart> exchangeChartList = rateList.stream()
                .map(data -> MaResExchangeRateDto.ExchangeChart.builder()
                        .eDate(data.getDate().toString())
                        .eChat(data.getClosingPrice().longValue())
                        .build())
                .toList();

        // 어제와 그저께 데이터 (정렬 후 뒤에서 2개)
        ExchangeRateEn yesterday = rateList.get(rateList.size() - 1);
        ExchangeRateEn dayBefore = rateList.get(rateList.size() - 2);

        BigDecimal yClose = yesterday.getClosingPrice();
        BigDecimal dClose = dayBefore.getClosingPrice();

        // 증감 계산
        BigDecimal diff = yClose.subtract(dClose);
        long indecrease = diff.longValue();

        // 증감률 계산
        double percentage = dClose.compareTo(BigDecimal.ZERO) == 0
                ? 0.0
                : diff.divide(dClose, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100))
                .doubleValue();

        // 환율 전일 종가 정보 DTO
        MaResDto.PreviousClose previousClose = MaResDto.PreviousClose.builder()
                .price(yClose.longValue())
                .indecrease(indecrease)
                .percentage(percentage)
                .build();

        // 환율 정보 DTO
        MaResExchangeRateDto exchangeRateDto = MaResExchangeRateDto.builder()
                .exchangePreviousClose(previousClose)
                .exchangeChat(exchangeChartList)
                .build();

        // 최종 응답
        return MaResDto.builder()
                .code("SU")
                .message("Success")
                .exchangeRate(exchangeRateDto)
                .build();
    }

    public MaResDto oilPrice() {
        List<MaResOilPriceDto.OilTypeData> oilTypeDataList = new ArrayList<>();

        for (OilType oilType : OilType.values()) {
            List<OilPriceEn> oilList = oilPriceRe.findLatest15DaysByOilType(oilType.name());

            if (oilList.size() < 2) {
                continue; // 데이터가 부족하면 해당 유종은 제외
            }

            // 날짜 오름차순 정렬
            oilList.sort(Comparator.comparing(OilPriceEn::getDate));

            // 차트 데이터 생성
            List<MaResOilPriceDto.OilChart> oilCharts = oilList.stream()
                    .map(item -> MaResOilPriceDto.OilChart.builder()
                            .oDate(item.getDate().toString())
                            .oChat(item.getAveragePriceCompetition().longValue())
                            .build())
                    .toList();

            // 가장 최근 2개 데이터 (전일, 전전일)
            OilPriceEn yesterday = oilList.get(oilList.size() - 1);
            OilPriceEn dayBefore = oilList.get(oilList.size() - 2);

            BigDecimal yPrice = yesterday.getAveragePriceCompetition();
            BigDecimal dPrice = dayBefore.getAveragePriceCompetition();
            BigDecimal diff = yPrice.subtract(dPrice);
            long indecrease = diff.longValue();

            double percentage = dPrice.compareTo(BigDecimal.ZERO) == 0
                    ? 0.0
                    : diff.divide(dPrice, 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100)).doubleValue();

            MaResDto.PreviousClose previousClose = MaResDto.PreviousClose.builder()
                    .price(yPrice.longValue())
                    .indecrease(indecrease)
                    .percentage(percentage)
                    .build();

            // 유종별 데이터 담기
            MaResOilPriceDto.OilTypeData typeData = MaResOilPriceDto.OilTypeData.builder()
                    .oilType(oilType.name())
                    .oilChat(oilCharts)
                    .oilPreviousClose(previousClose)
                    .build();

            oilTypeDataList.add(typeData);
        }

        // 전체 유가 DTO 구성
        MaResOilPriceDto oilDto = MaResOilPriceDto.builder()
                .oilTypes(oilTypeDataList)
                .build();

        return MaResDto.builder()
                .code("SU")
                .message("Success")
                .oilPrice(oilDto)
                .build();
    }

    public MaResDto tradingVolumeTop10() {
        List<StockPriceEn> recentList = stockPriceRe.findTop10ByRecentDateOrderByTradingVolumeDesc();

        List<MaResTradingVolumeDto> resultList = new ArrayList<>();

        for (StockPriceEn recentStock : recentList) {
            long recentVolume = recentStock.getTradingVolume();
            long volumeDiff = recentStock.getPriceChange();
            BigDecimal volumeRate = recentStock.getPriceChangeRate();


            resultList.add(MaResTradingVolumeDto.builder()
                    .stocks(recentStock.getStockInfo().getKorStockName())
                    .volume(recentVolume)
                    .volumeIndecrease(volumeDiff)
                    .volumePercentage(volumeRate)
                    .build());
        }

        return MaResDto.builder()
                .code("SU")
                .message("Success")
                .tradingVolume(resultList)
                .build();
    }
}