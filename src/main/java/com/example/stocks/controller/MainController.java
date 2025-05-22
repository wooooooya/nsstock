package com.example.stocks.controller;

import com.example.stocks.service.StockServiceSer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
@RestController
@RequestMapping("/stock")
@RequiredArgsConstructor

public class MainController {
    private final StockServiceSer StockServiceSer;

}

//    @GetMapping("")
//    public List<ToDoList> getAllToDos() {
//        return toDoListService.getAllToDos();
//    }