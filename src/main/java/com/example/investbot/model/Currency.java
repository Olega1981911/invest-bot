package com.example.investbot.model;

public enum Currency {
    RUB("RUB"),
    USD("USD"),
    EUR("EUR"),
    GBR("GBR"),
    HKD("HKD"),
    CHF("CHF"),
    JPY("JPY"),
    CNY("CNY"),
    TRY("TRY");
    String currency;

    Currency(String currency) {
        this.currency = currency;
    }
}
