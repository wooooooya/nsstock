package com.example.stocks.dto.home;

import com.example.stocks.dto.common.ChartDataDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

// 각 위젯에 한 번에 출력
@Getter
@Builder
public class DashboardResponseDto {
    private KospiWidgetDto kospiIndex;
    private GoldPriceWidgetDto goldPrice;
    private OilPriceWidgetDto oilPrice;
    private List<TopTradingWidgetDto> topTrading;
    private List<FavoriteWidgetDto> favorites;
    private List<ChartDataDto> kospiChart;
}