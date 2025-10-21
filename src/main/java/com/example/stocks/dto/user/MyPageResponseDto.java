package com.example.stocks.dto.user;

import com.example.stocks.dto.home.FavoriteWidgetDto;
import com.example.stocks.entity.user.UserInfoEn;
import lombok.Getter;

import java.util.List;

// MyPage 탭 Dto
@Getter
public class MyPageResponseDto {
    private final String loginId;
    private final String email;
    private final List<FavoriteWidgetDto> favorites;

    public MyPageResponseDto(UserInfoEn user, List<FavoriteWidgetDto> favorites) {
        this.loginId = user.getLoginId();
        this.email = user.getEmail();
        this.favorites = favorites;
    }
}