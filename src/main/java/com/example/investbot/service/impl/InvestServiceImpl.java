package com.example.investbot.service.impl;

import com.example.investbot.dto.StocksDto;
import com.example.investbot.dto.TickersDto;
import com.example.investbot.model.Currency;
import com.example.investbot.model.Stock;
import com.example.investbot.service.InvestService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.tinkoff.invest.openapi.OpenApi;
import ru.tinkoff.invest.openapi.model.rest.MarketInstrument;
import ru.tinkoff.invest.openapi.model.rest.MarketInstrumentList;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvestServiceImpl implements InvestService {

    private final OpenApi openApi;
    @Override
    public Stock getStockByTicker(String ticker) {
        CompletableFuture<MarketInstrumentList> cf = getMarketInstrumentTicker(ticker);
        MarketInstrumentList marketInstrumentList = cf.join();

        if (marketInstrumentList.getInstruments().isEmpty()) {
            throw new IllegalArgumentException("Тикер " + ticker + " не найден.");
        }

        MarketInstrument item = marketInstrumentList.getInstruments().get(0);

        return new Stock(
                item.getTicker(),
                item.getFigi(),
                item.getName(),
                item.getType().getValue(),
                Currency.valueOf(item.getCurrency().getValue()),
                "TINKOFF"
        );
    }

    @Override
    public StocksDto getStocksByTickers(TickersDto tickers) {
        List<Stock> stocks = tickers.getTickers().stream()
                .map(this::getMarketInstrumentTicker)
                .map(CompletableFuture::join)
                .filter(mi -> !mi.getInstruments().isEmpty())
                .map(mi -> mi.getInstruments().get(0))
                .map(mi -> new Stock(
                        mi.getTicker(),
                        mi.getFigi(),
                        mi.getName(),
                        mi.getType().getValue(),
                        Currency.valueOf(mi.getCurrency().getValue()),
                        "TINKOFF"
                ))
                .collect(Collectors.toList());

        return new StocksDto(stocks);
    }

    @Async
    public CompletableFuture<MarketInstrumentList> getMarketInstrumentTicker(String ticker) {
        var context = openApi.getMarketContext();
        return context.searchMarketInstrumentsByTicker(ticker);
    }
}
