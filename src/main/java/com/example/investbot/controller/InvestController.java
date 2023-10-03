package com.example.investbot.controller;

import com.example.investbot.dto.StocksDto;
import com.example.investbot.dto.TickersDto;
import com.example.investbot.model.Stock;
import com.example.investbot.service.InvestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class InvestController {
   private final InvestService investService;

    @GetMapping("/stocks/{ticker}")
    public Stock getStock(@PathVariable String ticker) {
        return investService.getStockByTicker(ticker);

    }
    @PostMapping("/stocks/getStocksByTickers")
    public StocksDto getStocksByTickers(@RequestBody TickersDto tickersDto) {
        return investService.getStocksByTickers(tickersDto);
    }
}
