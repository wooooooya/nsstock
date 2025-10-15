package com.example.stocks.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

// 위젯 정보 Dto
@Getter
@Setter
public class WidgetLayoutDto {
    private List<WidgetItem> widgets;

    @Getter
    @Setter
    public static class WidgetItem {
        private String widgetId;
        private int position;
    }
}