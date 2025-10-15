package com.example.stocks.service;

import com.example.stocks.dto.home.FavoriteWidgetDto;
import com.example.stocks.dto.user.*;
import com.example.stocks.entity.user.UserFavoriteEn;
import com.example.stocks.entity.user.UserFavoriteId;
import com.example.stocks.entity.user.UserInfoEn;
import com.example.stocks.entity.user.UserWidgetSettingsEn;
import com.example.stocks.entity.user.enumeration.WidgetType;
import com.example.stocks.repository.stock.StockPriceRe;
import com.example.stocks.repository.user.UserFavoriteRe;
import com.example.stocks.repository.user.UserInfoRe;
import com.example.stocks.repository.user.UserWidgetSettingsRe;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserInfoRe userInfoRepository;
    private final UserFavoriteRe userFavoriteRepository;
    private final UserWidgetSettingsRe userWidgetSettingsRepository;
    private final StockPriceRe stockPriceRepository;

    @Transactional
    public void signUp(UserSignUpRequestDto requestDto) {
        if (userInfoRepository.findByLoginId(requestDto.getLoginId()).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }

        UserInfoEn newUser = new UserInfoEn();
        newUser.setUuid(UUID.randomUUID().toString());
        newUser.setLoginId(requestDto.getLoginId());
        newUser.setPw(requestDto.getPassword());
        newUser.setEmail(requestDto.getEmail());
        userInfoRepository.save(newUser);
        userInfoRepository.flush();

        List<UserWidgetSettingsEn> defaultWidgets = List.of(
                new UserWidgetSettingsEn(newUser, WidgetType.FAVORITES.name(), 6),
                new UserWidgetSettingsEn(newUser, WidgetType.KOSPI_INDEX.name(), 1),
                new UserWidgetSettingsEn(newUser, WidgetType.TRADING_TOP5.name(), 5),
                new UserWidgetSettingsEn(newUser, WidgetType.GOLD_PRICE.name(), 7),
                new UserWidgetSettingsEn(newUser, WidgetType.KOSPI_CHART.name(), 3),
                new UserWidgetSettingsEn(newUser, WidgetType.OIL_PRICE.name(), 8)
        );
        userWidgetSettingsRepository.saveAll(defaultWidgets);
    }

    public String login(UserLoginRequestDto requestDto) {
        UserInfoEn user = userInfoRepository.findByLoginId(requestDto.getLoginId())
                .orElseThrow(() -> new IllegalArgumentException("아이디가 존재하지 않습니다."));

        if (!user.getPw().equals(requestDto.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        return "로그인 성공!";
    }

    @Transactional
    public void updatePassword(String loginId, UserPasswordUpdateRequestDto requestDto) {
        UserInfoEn user = userInfoRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        if (!user.getPw().equals(requestDto.getCurrentPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }
        user.setPw(requestDto.getNewPassword());
    }

    @Transactional
    public void withdraw(UserWithdrawalRequestDto requestDto) {
        UserInfoEn user = userInfoRepository.findByLoginId(requestDto.getLoginId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (!user.getPw().equals(requestDto.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        userInfoRepository.delete(user);
    }

    @Transactional
    public void saveWidgetLayout(String loginId, WidgetLayoutDto layoutDto) {
        UserInfoEn user = userInfoRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        for (WidgetLayoutDto.WidgetItem widget : layoutDto.getWidgets()) {
            WidgetType type = WidgetType.valueOf(widget.getWidgetId());
            if (type.getCellSize() == 2) {
                if (widget.getPosition() == 4 || widget.getPosition() == 8) {
                    throw new IllegalArgumentException("2칸 위젯은 맨 오른쪽에 위치할 수 없습니다.");
                }
            }
        }

        userWidgetSettingsRepository.deleteAllByUserInfo(user);
        userWidgetSettingsRepository.flush();

        List<UserWidgetSettingsEn> newSettings = layoutDto.getWidgets().stream()
                .map(widget -> new UserWidgetSettingsEn(user, widget.getWidgetId(), widget.getPosition()))
                .collect(Collectors.toList());
        userWidgetSettingsRepository.saveAll(newSettings);
    }

    @Transactional
    public String toggleFavorite(String loginId, FavoriteToggleRequestDto requestDto) {
        UserInfoEn user = userInfoRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        UserFavoriteId favoriteId = new UserFavoriteId(user.getUuid(), requestDto.getShortCode());

        if (userFavoriteRepository.existsById(favoriteId)) {
            userFavoriteRepository.deleteById(favoriteId);
            return "DELETED";
        } else {
            UserFavoriteEn newFavorite = new UserFavoriteEn(user, requestDto.getShortCode());
            userFavoriteRepository.save(newFavorite);
            return "ADDED";
        }
    }

    @Transactional(readOnly = true)
    public MyPageResponseDto getMyPageData(String loginId) {
        UserInfoEn user = userInfoRepository.findByLoginId(loginId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        List<FavoriteWidgetDto> favorites = getFavoritesWithPrices(user.getUuid());

        return new MyPageResponseDto(user, favorites);
    }

    /**
     * 특정 사용자의 즐겨찾기 목록과 최신 가격 정보만 조회합니다. (위젯용)
     */
    @Transactional(readOnly = true)
    public List<FavoriteWidgetDto> getFavoritesWidgetData(String loginId) {
        UserInfoEn user = userInfoRepository.findByLoginId(loginId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        // 기존에 만들어둔 private 헬퍼 메서드를 재사용합니다.
        return getFavoritesWithPrices(user.getUuid());
    }

    // private 헬퍼 메서드
    private List<FavoriteWidgetDto> getFavoritesWithPrices(String userUuid) {
        List<String> favoriteShortCodes = userFavoriteRepository.findAllByUserInfo_Uuid(userUuid)
                .stream()
                .map(UserFavoriteEn::getShortCode)
                .collect(Collectors.toList());

        if (favoriteShortCodes.isEmpty()) {
            return new ArrayList<>();
        }

        return stockPriceRepository.findLatestPricesByShortCodes(favoriteShortCodes);
    }
}