package com.example.stocks.dto.user;

import lombok.Getter;
import lombok.Setter;

// 즐겨찾기 등록 및 해제 Dto
@Getter
@Setter
public class FavoriteToggleRequestDto {
    private String shortCode;
}