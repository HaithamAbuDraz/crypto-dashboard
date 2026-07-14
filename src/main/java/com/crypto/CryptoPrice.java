package com.crypto;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CryptoPrice {
    private final String symbol;
    private final String name;
    private double currentPrice;
    private double previousPrice;
    private double changePercent;
    private String lastUpdated;
    
    private static final DateTimeFormatter TIME_FORMATTER = 
        DateTimeFormatter.ofPattern("HH:mm:ss");

    public CryptoPrice(String symbol, String name) {
        this.symbol = symbol;
        this.name = name;
        this.currentPrice = 0.0;
        this.previousPrice = 0.0;
        this.changePercent = 0.0;
        this.lastUpdated = "--:--:--";
    }

    public void updatePrice(double newPrice) {
        this.previousPrice = this.currentPrice;
        this.currentPrice = newPrice;
        
        if (previousPrice > 0) {
            this.changePercent = ((currentPrice - previousPrice) / previousPrice) * 100;
        }
        this.lastUpdated = LocalTime.now().format(TIME_FORMATTER);
    }

    public String getSymbol() { return symbol; }
    public String getName() { return name; }
    public double getCurrentPrice() { return currentPrice; }
    public double getPreviousPrice() { return previousPrice; }
    public double getChangePercent() { return changePercent; }
    public String getLastUpdated() { return lastUpdated; }
    
    public boolean isPriceUp() { return currentPrice > previousPrice; }
    public boolean isPriceDown() { return currentPrice < previousPrice && previousPrice > 0; }
}