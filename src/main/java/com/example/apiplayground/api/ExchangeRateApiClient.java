// src/main/java/com/example/apiplayground/api/ExchangeRateApiClient.java
package com.example.apiplayground.api;

import com.example.apiplayground.model.ExchangeRatesResponse;
import com.example.apiplayground.model.SupportedCodesResponse;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class ExchangeRateApiClient {
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6";

    private final String apiKey;
    private final HttpClient http;
    private final Gson gson;

    public ExchangeRateApiClient(String apiKey) {
        this.apiKey = apiKey;
        this.http = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        this.gson = new Gson();
    }

    public ExchangeRatesResponse getLatestRates(String baseCurrency) throws IOException, InterruptedException {
        String base = normalizeCode(baseCurrency);
        String url = BASE_URL + "/" + apiKey + "/latest/" + base;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(15))
                .GET()
                .header("Accept", "application/json")
                .build();

        HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new RuntimeException("HTTP " + response.statusCode() + " al consultar latest.");
        }

        ExchangeRatesResponse parsed = gson.fromJson(response.body(), ExchangeRatesResponse.class);
        if (parsed == null) throw new RuntimeException("JSON inválido (latest).");

        if (parsed.result == null || !parsed.result.equalsIgnoreCase("success")) {
            throw new RuntimeException(parsed.errorType != null ? parsed.errorType : "Respuesta no exitosa (latest).");
        }

        return parsed;
    }

    public SupportedCodesResponse getSupportedCodes() throws IOException, InterruptedException {
        // Endpoint documentado: /codes :contentReference[oaicite:1]{index=1}
        String url = BASE_URL + "/" + apiKey + "/codes";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(15))
                .GET()
                .header("Accept", "application/json")
                .build();

        HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new RuntimeException("HTTP " + response.statusCode() + " al consultar codes.");
        }

        SupportedCodesResponse parsed = gson.fromJson(response.body(), SupportedCodesResponse.class);
        if (parsed == null) throw new RuntimeException("JSON inválido (codes).");

        if (parsed.result == null || !parsed.result.equalsIgnoreCase("success")) {
            throw new RuntimeException(parsed.errorType != null ? parsed.errorType : "Respuesta no exitosa (codes).");
        }

        return parsed;
    }

    private static String normalizeCode(String code) {
        if (code == null || code.isBlank()) throw new IllegalArgumentException("Código de moneda requerido.");
        return code.trim().toUpperCase();
    }
}
