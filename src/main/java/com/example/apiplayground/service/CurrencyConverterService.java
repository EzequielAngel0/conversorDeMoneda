// src/main/java/com/example/apiplayground/service/CurrencyConverterService.java
package com.example.apiplayground.service;

import com.example.apiplayground.api.ExchangeRateApiClient;
import com.example.apiplayground.model.CurrencyItem;
import com.example.apiplayground.model.ExchangeRatesResponse;
import com.example.apiplayground.model.SupportedCodesResponse;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class CurrencyConverterService {
    private final ExchangeRateApiClient client;

    private static final Duration TTL = Duration.ofMinutes(10);

    private final Map<String, RatesCacheEntry> ratesCacheByBase = new ConcurrentHashMap<>();
    private CodesCacheEntry codesCache = null;

    public CurrencyConverterService(ExchangeRateApiClient client) {
        this.client = client;
    }

    public BigDecimal convert(BigDecimal amount, String from, String to) throws Exception {
        if (amount == null) throw new IllegalArgumentException("Monto requerido.");
        if (amount.signum() < 0) throw new IllegalArgumentException("El monto no puede ser negativo.");

        String base = normalizeCode(from);
        String target = normalizeCode(to);

        ExchangeRatesResponse rates = getRatesCached(base);
        Double rateDouble = (rates.conversion_rates != null) ? rates.conversion_rates.get(target) : null;

        if (rateDouble == null) {
            throw new IllegalArgumentException("Moneda destino no soportada para base " + base + ": " + target);
        }

        BigDecimal result = amount.multiply(BigDecimal.valueOf(rateDouble));
        return result.setScale(2, RoundingMode.HALF_UP);
    }

    public List<CurrencyItem> getSupportedCurrencies() throws Exception {
        CodesCacheEntry entry = codesCache;
        if (entry != null && !entry.isExpired()) {
            return entry.items;
        }

        SupportedCodesResponse resp = client.getSupportedCodes();
        if (resp.supportedCodes == null || resp.supportedCodes.isEmpty()) {
            throw new RuntimeException("No se recibió supported_codes.");
        }

        ArrayList<CurrencyItem> items = new ArrayList<>(resp.supportedCodes.size());
        for (List<String> pair : resp.supportedCodes) {
            if (pair == null || pair.size() < 2) continue;
            String code = normalizeCode(pair.get(0));
            String name = pair.get(1) != null ? pair.get(1).trim() : code;
            items.add(new CurrencyItem(code, name));
        }

        items.sort(Comparator.comparing(CurrencyItem::toString, String.CASE_INSENSITIVE_ORDER));
        codesCache = new CodesCacheEntry(Collections.unmodifiableList(items), Instant.now());
        return codesCache.items;
    }

    private ExchangeRatesResponse getRatesCached(String base) throws Exception {
        RatesCacheEntry entry = ratesCacheByBase.get(base);
        if (entry != null && !entry.isExpired()) return entry.data;

        ExchangeRatesResponse fresh = client.getLatestRates(base);
        ratesCacheByBase.put(base, new RatesCacheEntry(fresh, Instant.now()));
        return fresh;
    }

    private static String normalizeCode(String code) {
        if (code == null || code.isBlank()) throw new IllegalArgumentException("Código de moneda requerido.");
        return code.trim().toUpperCase();
    }

    private static final class RatesCacheEntry {
        private final ExchangeRatesResponse data;
        private final Instant storedAt;

        private RatesCacheEntry(ExchangeRatesResponse data, Instant storedAt) {
            this.data = data;
            this.storedAt = storedAt;
        }

        private boolean isExpired() {
            return Instant.now().isAfter(storedAt.plus(TTL));
        }
    }

    private static final class CodesCacheEntry {
        private final List<CurrencyItem> items;
        private final Instant storedAt;

        private CodesCacheEntry(List<CurrencyItem> items, Instant storedAt) {
            this.items = items;
            this.storedAt = storedAt;
        }

        private boolean isExpired() {
            return Instant.now().isAfter(storedAt.plus(TTL));
        }
    }
}
