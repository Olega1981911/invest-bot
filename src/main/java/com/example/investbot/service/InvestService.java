package com.example.investbot.service;

import com.example.investbot.dto.StocksDto;
import com.example.investbot.dto.TickersDto;
import com.example.investbot.model.Stock;

public interface InvestService {
    Stock getStockByTicker(String ticker);

    StocksDto getStocksByTickers(TickersDto tickers);
}
