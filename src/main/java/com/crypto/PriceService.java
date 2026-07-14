package com.crypto;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class PriceService {
    private static final String BINANCE_API = "https://api.binance.com/api/v3/ticker/price?symbol=";
    private final HttpClient httpClient;

    public PriceService() {
        this.httpClient = HttpClient.newBuilder()
            .connectTimeout(java.time.Duration.ofSeconds(5))
            .build();
    }

    public CompletableFuture<Double> fetchPrice(String symbol) {
        String url = BINANCE_API + symbol;
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .GET()
            .header("Accept", "application/json")
            .timeout(java.time.Duration.ofSeconds(5))
            .build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
            .thenApply(response -> {
                if (response.statusCode() != 200) {
                    throw new RuntimeException("API Error: " + response.statusCode());
                }
                JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
                return json.get("price").getAsDouble();
            })
            .exceptionally(throwable -> {
                System.err.println("Failed to fetch " + symbol + ": " + throwable.getMessage());
                return null;
            });
    }
}