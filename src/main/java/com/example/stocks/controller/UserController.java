package com.example.stocks.controller;

import com.example.stocks.dto.home.FavoriteWidgetDto;
import com.example.stocks.dto.user.*;
import com.example.stocks.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 마이페이지, 비밀번호 변경, 탈퇴, 위젯, 즐겨찾기 등
 * 로그인한 사용자와 관련된 모든 API를 담당합니다.
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    /**
     * [마이페이지] 페이지 전체에 필요한 데이터(사용자 정보 + 즐겨찾기 목록)를 조회합니다.
     */
    @GetMapping("/{loginId}")
    public MyPageResponseDto getMyPageData(@PathVariable String loginId) {
        return userService.getMyPageData(loginId);
    }

    /**
     * [마이페이지] 비밀번호를 수정합니다.
     */
    @PatchMapping("/{loginId}/password")
    public ResponseEntity<String> updatePassword(@PathVariable String loginId, @RequestBody UserPasswordUpdateRequestDto requestDto) {
        userService.updatePassword(loginId, requestDto);
        return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
    }

    /**
     * [마이페이지] 회원 계정을 탈퇴합니다.
     */
    @PostMapping("/withdraw")
    public ResponseEntity<String> withdraw(@RequestBody UserWithdrawalRequestDto requestDto) {
        userService.withdraw(requestDto);
        return ResponseEntity.ok("회원 탈퇴 처리가 완료되었습니다.");
    }

    /**
     * [즐겨찾기] 특정 종목을 즐겨찾기에 추가하거나 삭제(토글)합니다.
     */
    @PostMapping("/{loginId}/favorites/toggle")
    public ResponseEntity<String> toggleFavorite(@PathVariable String loginId, @RequestBody FavoriteToggleRequestDto requestDto) {
        String status = userService.toggleFavorite(loginId, requestDto);
        return ResponseEntity.ok(status);
    }

    /**
     * [즐겨찾기 위젯] 사용자의 즐겨찾기 목록만 조회합니다. (대시보드 위젯용)
     */
    @GetMapping("/{loginId}/favorites")
    public List<FavoriteWidgetDto> getFavoritesWidget(@PathVariable String loginId) {
        return userService.getFavoritesWidgetData(loginId);
    }

    /**
     * [위젯] 사용자가 변경한 위젯 레이아웃을 저장합니다.
     */
    @PutMapping("/{loginId}/widgets")
    public ResponseEntity<String> saveWidgetLayout(@PathVariable String loginId, @RequestBody WidgetLayoutDto layoutDto) {
        userService.saveWidgetLayout(loginId, layoutDto);
        return ResponseEntity.ok("위젯 레이아웃이 저장되었습니다.");
    }
}