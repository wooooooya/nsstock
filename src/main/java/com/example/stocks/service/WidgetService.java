package com.example.stocks.service;

import com.example.stocks.dto.common.ChartDataDto;
import com.example.stocks.dto.home.DashboardResponseDto;
import com.example.stocks.dto.home.*;
import com.example.stocks.entity.market.KospiIndexEn;
import com.example.stocks.entity.market.enumeration.GoldType;
import com.example.stocks.entity.market.enumeration.OilType;
import com.example.stocks.entity.user.UserInfoEn;
import com.example.stocks.repository.stock.StockPriceRe;
import com.example.stocks.repository.market.GoldPriceRe;
import com.example.stocks.repository.stock.KospiIndexRe;
import com.example.stocks.repository.market.OilPriceRe;
import com.example.stocks.repository.user.UserFavoriteRe;
import com.example.stocks.repository.user.UserInfoRe;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors; // Collectors import 추가

@Service
@RequiredArgsConstructor
public class WidgetService {

    private final KospiIndexRe kospiIndexRepository;
    private final GoldPriceRe goldPriceRepository;
    private final OilPriceRe oilPriceRepository;
    private final UserInfoRe userInfoRepository;
    private final UserFavoriteRe userFavoriteRepository;
    private final StockPriceRe stockPriceRepository;

    @Transactional(readOnly = true)
    public KospiWidgetDto getLatestKospiIndex() {
        return kospiIndexRepository.findFirstByOrderByDateDesc()
                .map(KospiWidgetDto::new)
                .orElseThrow(() -> new RuntimeException("코스피 지수 데이터를 찾을 수 없습니다."));
    }

    @Transactional(readOnly = true)
    public GoldPriceWidgetDto getLatestGoldPrices() {
        var gold1kg = goldPriceRepository.findFirstByGoldTypeOrderByDateDesc(GoldType.GOLD_1KG).orElse(null);
        var gold100g = goldPriceRepository.findFirstByGoldTypeOrderByDateDesc(GoldType.MINI_GOLD_100G).orElse(null);
        return new GoldPriceWidgetDto(gold1kg, gold100g);
    }

    @Transactional(readOnly = true)
    public OilPriceWidgetDto getLatestOilPrices() {
        var gasoline = oilPriceRepository.findFirstByOilTypeOrderByDateDesc(OilType.GASOLINE).orElse(null);
        var diesel = oilPriceRepository.findFirstByOilTypeOrderByDateDesc(OilType.DIESEL).orElse(null);
        var kerosene = oilPriceRepository.findFirstByOilTypeOrderByDateDesc(OilType.KEROSENE).orElse(null);
        return new OilPriceWidgetDto(gasoline, diesel, kerosene);
    }

    @Transactional(readOnly = true)
    public List<ChartDataDto> getKospiChartData(String period) {
        LocalDate now = LocalDate.now();
        LocalDate startDate;

        switch (period.toLowerCase()) {
            case "1m": startDate = now.minusMonths(1); break;
            case "6m": startDate = now.minusMonths(6); break;
            case "1y": default: startDate = now.minusYears(1); break;
        }

        List<KospiIndexEn> kospiData = kospiIndexRepository.findByDateGreaterThanEqualOrderByDateAsc(startDate);

        return kospiData.stream()
                .map(data -> new ChartDataDto(data.getDate(), data.getClosingPrice()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<FavoriteWidgetDto> getFavoritesWidgetData(String loginId) {
        UserInfoEn user = userInfoRepository.findByLoginId(loginId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        List<String> favoriteShortCodes = userFavoriteRepository.findAllByUserInfo(user)
                .stream()
                .map(fav -> fav.getShortCode())
                .collect(Collectors.toList()); // toList() -> collect(Collectors.toList())

        if (favoriteShortCodes.isEmpty()) {
            return new ArrayList<>();
        }
        return stockPriceRepository.findLatestPricesByShortCodes(favoriteShortCodes);
    }

    @Transactional(readOnly = true)
    public List<TopTradingWidgetDto> getTop5TradingVolume() {
        Pageable top5 = PageRequest.of(0, 5);
        return stockPriceRepository.findTopTradingStocks(top5);
    }

    @Transactional(readOnly = true)
    public DashboardResponseDto getDashboardData(String loginId) {
        // 기존에 만들었던 개별 위젯 조회 메서드들을 모두 호출
        KospiWidgetDto kospiIndex = getLatestKospiIndex();
        GoldPriceWidgetDto goldPrice = getLatestGoldPrices();
        OilPriceWidgetDto oilPrice = getLatestOilPrices();
        List<TopTradingWidgetDto> topTrading = getTop5TradingVolume();
        List<FavoriteWidgetDto> favorites = getFavoritesWidgetData(loginId);
        List<ChartDataDto> kospiChart = getKospiChartData("1y"); // 차트 기본값은 1년

        // Builder를 사용해 DTO에 모든 데이터를 담아 반환
        return DashboardResponseDto.builder()
                .kospiIndex(kospiIndex)
                .goldPrice(goldPrice)
                .oilPrice(oilPrice)
                .topTrading(topTrading)
                .favorites(favorites)
                .kospiChart(kospiChart)
                .build();
    }
}