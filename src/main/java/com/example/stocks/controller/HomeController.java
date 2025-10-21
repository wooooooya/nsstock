package com.example.stocks.controller;

import com.example.stocks.dto.home.DashboardResponseDto;
import com.example.stocks.service.WidgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/home")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class HomeController {

    private final WidgetService widgetService;

    @GetMapping("/dashboard/{loginId}")
    public DashboardResponseDto getDashboard(@PathVariable String loginId) {
        return widgetService.getDashboardData(loginId);
    }
}