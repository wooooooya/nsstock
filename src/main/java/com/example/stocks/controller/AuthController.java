package com.example.stocks.controller;

import com.example.stocks.dto.user.UserLoginRequestDto;
import com.example.stocks.dto.user.UserSignUpRequestDto;
import com.example.stocks.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// 인증 (회원가입, 로그인)
@RestController
@RequestMapping("/api/auth") // 인증 관련 API는 /api/auth 경로 사용
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserSignUpRequestDto requestDto) {
        userService.signUp(requestDto);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginRequestDto requestDto) {
        String response = userService.login(requestDto);
        return ResponseEntity.ok(response);
    }
}