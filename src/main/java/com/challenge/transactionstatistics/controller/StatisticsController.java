package com.challenge.transactionstatistics.controller;

import com.challenge.transactionstatistics.model.Statistics;
import com.challenge.transactionstatistics.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/statistics")
public class StatisticsController {

    private StatisticsService statisticsService;

    @Autowired
    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping
    public ResponseEntity<Statistics> getLatestStatistics() {
        return ResponseEntity.ok(statisticsService.getStatistics());
    }
}
