package com.example.stocks.service;

import com.example.stocks.dto.*;
import com.example.stocks.entity.ExchangeRateEn;
import com.example.stocks.entity.GoldPriceEn;
import com.example.stocks.entity.KospiIndexEn;
import com.example.stocks.entity.OilPriceEn;
import com.example.stocks.entity.enumeration.GoldType;
import com.example.stocks.entity.enumeration.OilType;
import com.example.stocks.repository.ExchangeRateRe;
import com.example.stocks.repository.GoldPriceRe;
import com.example.stocks.repository.KospiIndexRe;
import com.example.stocks.repository.OilPriceRe;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StockServiceSer {
    private final KospiIndexRe kospiIndexRe; // 코스피
    private final ExchangeRateRe exchangePriceRe; // 환율
    private final OilPriceRe oilPriceRe; // 유가
//    private final GoldPriceRe goldPriceRe; // 금

    // 오늘 날짜 기준 계산
    LocalDate today = LocalDate.now();
    LocalDate startDate = today.minusDays(15); // 15일 전
    LocalDate yesterday = today.minusDays(1);   // 어제
    LocalDate dayBefore = today.minusDays(2);   // 그저께

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
        String currency = "USD";

        // 환율: 최근 15일 데이터 (USD 기준)
        List<ExchangeRateEn> rateList = exchangePriceRe.findLatest15DaysByCurrency(currency);

        if (rateList.size() < 2) {
            return null; // 최소 어제, 그저께가 있어야 함
        }

        // 날짜 오름차순 정렬
        rateList.sort(Comparator.comparing(ExchangeRateEn::getDate));

        // 차트 데이터 구성
        List<MaResExchangeRateDto.ExchangeChart> exchangeChartList = rateList.stream()
                .map(data -> MaResExchangeRateDto.ExchangeChart.builder()
                        .eDate(data.getDate().toString())
                        .eChat(data.getClosingPrice().longValue())
                        .build())
                .toList();

        // 어제와 그저께 데이터
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

        // 환율 정보 DTO 생성
        MaResExchangeRateDto exchangeRateDto = MaResExchangeRateDto.builder()
                .exchangePreviousClose(previousClose)
                .exchangeChat(exchangeChartList)
                .build();

        // 최종 응답 DTO 생성
        return MaResDto.builder()
                .code("SU")
                .message("Success")
                .exchangeRate(exchangeRateDto)
                .build();
    }
    
    public MaResDto oilPrice() {
        // 유가 종류 지정 (휘발유로 고정)
        OilType oilType = OilType.휘발유;

        // startDate ~ 어제까지의 유가 데이터 조회
        List<OilPriceEn> oilList = oilPriceRe.findAllBetweenDates(startDate, yesterday);

        // 날짜 기준 오름차순 정렬
        oilList.sort(Comparator.comparing(OilPriceEn::getDate));

        // 유가 데이터 중 지정한 oilType(휘발유)만 필터링해서 차트용 데이터로 변환
        List<MaResOilPriceDto.OilChart> oilCharts = oilList.stream()
                .filter(o -> o.getOilType() == oilType)
                .map(item -> MaResOilPriceDto.OilChart.builder()
                        .oChat(item.getAveragePriceCompetition().longValue()) // 평균 경쟁가격
                        .oDate(item.getDate().toString()) // 날짜 문자열
                        .build())
                .toList();

        // 어제 및 그 전날의 유가 데이터 조회
        Optional<OilPriceEn> yesterdayOpt = oilPriceRe.findByOilTypeAndDate(oilType, yesterday);
        Optional<OilPriceEn> dayBeforeOpt = oilPriceRe.findByOilTypeAndDate(oilType, dayBefore);

        // 두 날짜 중 하나라도 데이터가 없으면 null 반환
        if (yesterdayOpt.isEmpty() || dayBeforeOpt.isEmpty()) {
            return null;
        }

        // 어제와 그 전날의 평균 경쟁가격
        BigDecimal yesterdayPrice = yesterdayOpt.get().getAveragePriceCompetition();
        BigDecimal dayBeforePrice = dayBeforeOpt.get().getAveragePriceCompetition();

        // 가격 차이 계산
        BigDecimal diff = yesterdayPrice.subtract(dayBeforePrice);
        long indecrease = diff.longValue(); // 증감 수치

        // 증감률(%) 계산 (0으로 나누는 경우 방지)
        double percentage = dayBeforePrice.compareTo(BigDecimal.ZERO) == 0
                ? 0.0
                : diff.divide(dayBeforePrice, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)).doubleValue();

        // 전일 종가 정보 생성
        MaResDto.PreviousClose previousClose = MaResDto.PreviousClose.builder()
                .price(yesterdayPrice.longValue()) // 전일 종가
                .indecrease(indecrease) // 전일 대비 증감
                .percentage(percentage) // 증감률
                .build();

        // 유가 응답 DTO 생성 (전일 종가 + 차트)
        MaResOilPriceDto oilDto = MaResOilPriceDto.builder()
                .oilPreviousClose(previousClose)
                .oilChat(oilCharts)
                .build();

        // 최종 응답 DTO 반환
        return MaResDto.builder()
                .code("SU") // 성공 코드
                .message("Success") // 성공 메시지
                .oilPrice(oilDto) // 유가 데이터 세팅
                .build();
    }

//    public MaResDto goldPrice() {
//        GoldType goldType = GoldType.GOLD_1KG;
//
//        // 1. 최신 날짜를 DB에서 조회
//        List<GoldPriceEn> latestTwo = goldPriceRe.findTop2ByGoldTypeOrderByDateDesc(goldType);
//
//        if (latestTwo.size() < 2) {
//            throw new RuntimeException("전일 또는 전전일 데이터가 부족합니다.");
//        }
//
//        GoldPriceEn yesterdayPrice = latestTwo.get(0);   // 가장 최근
//        GoldPriceEn dayBeforePrice = latestTwo.get(1);   // 바로 전날
//
//        int yClose = yesterdayPrice.getClosingPrice();
//        int dClose = dayBeforePrice.getClosingPrice();
//        int diff = yClose - dClose;
//
//        long indecrease = diff;
//        double percentage = (dClose == 0) ? 0.0 : ((double) diff / dClose) * 100;
//
//        MaResDto.PreviousClose previousClose = MaResDto.PreviousClose.builder()
//                .price(yClose)
//                .indecrease(indecrease)
//                .percentage(Math.round(percentage * 100.0) / 100.0)
//                .build();
//
//        // 2. 차트용 데이터 조회용: 가장 오래된 날짜 ~ 어제 날짜까지 조회
//        LocalDate startDate = LocalDate.now().minusDays(30); // 예: 최근 30일
//        LocalDate endDate = yesterdayPrice.getDate(); // 최신 날짜 기준
//
//        List<GoldPriceEn> goldList = goldPriceRe.findAllByGoldTypeAndDateBetweenOrderByDateAsc(
//                goldType, startDate, endDate
//        );
//
//        List<MaResGoldPriceDto.GoldChart> goldChartList = goldList.stream()
//                .map(item -> MaResGoldPriceDto.GoldChart.builder()
//                        .gChat((long) item.getClosingPrice())
//                        .gDate(item.getDate().toString())
//                        .build())
//                .toList();
//
//        MaResGoldPriceDto goldDto = MaResGoldPriceDto.builder()
//                .goldPreviousClose(previousClose)
//                .goldChat(goldChartList)
//                .build();
//
//        return MaResDto.builder()
//                .code("SU")
//                .message("Success")
//                .goldPrice(goldDto)
//                .build();
//    }
}