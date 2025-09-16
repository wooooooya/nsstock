package com.example.stocks.dto;

import java.util.Date;

public interface PreResStockListDto {
    String getStockID();
    String getStockName();
    Long getStockPrice();
    Long getVolume();
    Date getDate();
}