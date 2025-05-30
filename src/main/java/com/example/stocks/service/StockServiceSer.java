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
    private final GoldPriceRe goldPriceRe; // 금

    // 오늘 날짜 기준 계산
    LocalDate today = LocalDate.now();
    LocalDate startDate = today.minusDays(15); // 15일 전
    LocalDate yesterday = today.minusDays(1);   // 어제
    LocalDate dayBefore = today.minusDays(2);   // 그저께

    public MaResDto kospiIndex() {

        // 15일치 차트 데이터를 날짜 역순으로 한 번에 불러오기
        List<KospiIndexEn> indexList = kospiIndexRe.findAllBetweenDates(startDate, yesterday); // 코스피 지수

        // 날짜 오름차순 정렬
        indexList.sort((a, b) -> a.getDate().compareTo(b.getDate()));

        // 차트용 리스트 생성
        List<MaResKospiIndexDto.KospiChart> kospiChartList = indexList.stream()
                .map(item -> MaResKospiIndexDto.KospiChart.builder()
                        .kChat(item.getClosingPrice().longValue())
                        .kDate(item.getDate().toString())
                        .build())
                .toList();

        Optional<KospiIndexEn> yesterdayOpt = kospiIndexRe.findByDate(yesterday); // 어제의 데이터
        Optional<KospiIndexEn> dayBeforeOpt = kospiIndexRe.findByDate(dayBefore);  // 그저께의 데이터

        // 예외
        if (yesterdayOpt.isEmpty() || dayBeforeOpt.isEmpty()) {
            return null;
        }

        BigDecimal yesterdayPrice = yesterdayOpt.get().getClosingPrice(); // 어제의 종가
        BigDecimal dayBeforePrice = dayBeforeOpt.get().getClosingPrice(); // 그저께의 종가

// 증감 계산 (전일 종가 - 전전일 종가)
        BigDecimal diff = yesterdayPrice.subtract(dayBeforePrice);

// 증감 수치를 문자열로 포맷 (+ 또는 - 붙여서 출력) (예: +100, -50)
        String indecreaseFormatted = (diff.signum() >= 0 ? "+" : "") + diff.longValue();

// 증감률 계산 (증감 / 전전일 종가 * 100), 전전일 종가가 0이면 0.0으로 처리 (0으로 나누는 예외 방지)
        double percentage = dayBeforePrice.compareTo(BigDecimal.ZERO) == 0
                ? 0.0
                : diff.divide(dayBeforePrice, 4, RoundingMode.HALF_UP) // 소수점 4자리까지 계산
                .multiply(BigDecimal.valueOf(100)) // 백분율 변환
                .doubleValue();

// 증감률을 문자열로 포맷 (부호 포함, 소수점 둘째 자리까지)
// 예: +3.21, -2.45 형식으로 출력됨
        String percentageFormatted = String.format("%+.2f", percentage);

// 전일 종가 정보 생성 (MaResDto의 PreviousClose 객체 생성)
// - price: 어제 종가 (long)
// - indecrease: 전일 대비 증감 수치 (예: +100 또는 -50 → long 타입으로 저장)
// - percentage: 전일 대비 증감률 (예: +3.21% 또는 -2.45% → double 타입으로 저장)
        MaResDto.PreviousClose previousClose = MaResDto.PreviousClose.builder()
                .price(yesterdayPrice.longValue())
                .indecrease(Long.parseLong(indecreaseFormatted)) // 문자열 형태의 증감 수치를 long으로 변환하여 저장
                .percentage(Double.parseDouble(percentageFormatted)) // 문자열 형태의 증감률을 double로 변환하여 저장
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

    // 환율
    public MaResDto exchangeRate() {
        // 15일치 환율 데이터 불러오기
        List<ExchangeRateEn> rateList = exchangePriceRe.findAllBetweenDates(startDate, yesterday);

        // 날짜 오름차순 정렬
        rateList.sort((a, b) -> a.getDate().compareTo(b.getDate()));

        // 차트용 리스트 생성
        List<MaResExchangeRateDto.ExchangeChart> exchangeChats = rateList.stream()
                .map(item -> MaResExchangeRateDto.ExchangeChart.builder()
                        .eChat(item.getClosingPrice().longValue())
                        .eDate(item.getDate().toString())
                        .build())
                .toList();

        // 어제와 그저께의 USD 환율 조회
        Optional<ExchangeRateEn> yesterdayOpt = exchangePriceRe.findByCurrencyCodeANDDate("USD", yesterday);
        Optional<ExchangeRateEn> dayBeforeOpt = exchangePriceRe.findByCurrencyCodeANDDate("USD", dayBefore);

        if (yesterdayOpt.isEmpty() || dayBeforeOpt.isEmpty()) {
            return null;
        }

        BigDecimal yesterdayPrice = yesterdayOpt.get().getClosingPrice();
        BigDecimal dayBeforePrice = dayBeforeOpt.get().getClosingPrice();

        // 증감 계산
        BigDecimal diff = yesterdayPrice.subtract(dayBeforePrice);
        String indecreaseFormatted = (diff.signum() >= 0 ? "+" : "") + diff.longValue();

        // 증감률 계산
        double percentage = dayBeforePrice.compareTo(BigDecimal.ZERO) == 0
                ? 0.0
                : diff.divide(dayBeforePrice, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100)).doubleValue();
        String percentageFormatted = String.format("%+.2f", percentage);

        // 환율 전일 종가 정보 생성
        MaResDto.PreviousClose previousClose = MaResDto.PreviousClose.builder()
                .price(yesterdayPrice.longValue())
                .indecrease(Long.parseLong(indecreaseFormatted))
                .percentage(Double.parseDouble(percentageFormatted))
                .build();

        // 환율 정보 DTO 생성
        MaResExchangeRateDto exchangeDto = MaResExchangeRateDto.builder()
                .exchangePreviousClose(previousClose)
                .exchangeChat(exchangeChats)
                .build();

        // 최종 응답 DTO 생성
        return MaResDto.builder()
                .code("SU")
                .message("Success")
                .exchangeRate(exchangeDto)
                .build();
    }

    public MaResDto goldPrice() {
        GoldType goldType = GoldType.GOLD_1KG;

        // 1. 금 차트용 데이터 조회
        List<GoldPriceEn> goldList = goldPriceRe.findAllByGoldTypeAndDateBetween(
                goldType, startDate, yesterday
        );

        List<MaResGoldPriceDto.GoldChart> goldChartList = goldList.stream()
                .map(item -> MaResGoldPriceDto.GoldChart.builder()
                        .gChat((long) item.getClosingPrice())
                        .gDate(item.getDate().toString())
                        .build())
                .toList();

        // 2. 전일, 전전일 데이터 조회
        GoldPriceEn yesterdayPrice = goldPriceRe.findByGoldTypeAndDate(goldType, yesterday)
                .orElseThrow(() -> new RuntimeException("전일 데이터 없음"));

        GoldPriceEn dayBeforePrice = goldPriceRe.findByGoldTypeAndDate(goldType, dayBefore)
                .orElseThrow(() -> new RuntimeException("전전일 데이터 없음"));

        int yClose = yesterdayPrice.getClosingPrice();
        int dClose = dayBeforePrice.getClosingPrice();
        int diff = yClose - dClose;

        long indecrease = diff;
        double percentage = (dClose == 0) ? 0.0 : ((double) diff / dClose) * 100;

        MaResDto.PreviousClose previousClose = MaResDto.PreviousClose.builder()
                .price(yClose)
                .indecrease(indecrease)
                .percentage(Math.round(percentage * 100.0) / 100.0) // 소수점 2자리 반올림
                .build();

        MaResGoldPriceDto goldDto = MaResGoldPriceDto.builder()
                .goldPreviousClose(previousClose)
                .goldChat(goldChartList)
                .build();

        return MaResDto.builder()
                .code("SU")
                .message("Success")
                .goldPrice(goldDto)
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
}