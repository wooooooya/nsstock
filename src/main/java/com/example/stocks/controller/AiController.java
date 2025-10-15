package com.example.stocks.controller;

import com.example.stocks.dto.ai.StockPredictionDto;
import com.example.stocks.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AiController {

    private final AiService aiService;

    @GetMapping("/predictions")
    public Collection<StockPredictionDto> getAllPredictions() {
        return aiService.getAllLatestPredictions();
    }
}