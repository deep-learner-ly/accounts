package com.example.bookkeeping.controller;

import com.example.bookkeeping.model.ChartData;
import com.example.bookkeeping.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

@RestController
@RequestMapping("/api/stats")
public class StatsController {

    @Autowired
    private StatsService statsService;

    @GetMapping("/chart")
    public ResponseEntity<List<ChartData>> getChartData(@RequestParam(defaultValue = "day") String type,
                                                        HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        if (userId == null) userId = 1L;
        
        return ResponseEntity.ok(statsService.getStats(userId, type));
    }
}
