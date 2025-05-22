package com.example.stocks.service;

import com.example.stocks.dto.MaResDto;
import com.example.stocks.dto.MaResKospiIndexDto;
import com.example.stocks.entity.KospiIndexEn;
import com.example.stocks.repository.KospiIndexRe;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StockServiceSer {
    private final KospiIndexRe kospiIndexRe;

    public MaResDto kospiIndex() {
        // 오늘 날짜 기준
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusDays(15); // 15일 전
        LocalDate yesterday = today.minusDays(1);   // 어제
        LocalDate dayBefore = today.minusDays(2);   // 그저께

        // 15일치 차트 데이터를 날짜 역순으로 한 번에 불러오기
        List<KospiIndexEn> indexList = kospiIndexRe.findAllBetweenDates(startDate, yesterday);

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
                .previousClose(previousClose)
                .kospiChat(kospiChartList)
                .build();

        // 최종 응답 DTO 생성
        return MaResDto.builder()
                .code("SU")
                .message("Success")
                .kospiRate(kospiDto)
                .build();
    }
}