// src/main/java/com/example/apiplayground/util/CurrencyNames.java
package com.example.apiplayground.util;

import java.util.Map;

public final class CurrencyNames {

    private CurrencyNames() {}

    public static final Map<String, String> NAMES = Map.ofEntries(
            Map.entry("USD", "Dólar estadounidense"),
            Map.entry("MXN", "Pesos mexicanos"),
            Map.entry("EUR", "Euro"),
            Map.entry("GBP", "Libra esterlina"),
            Map.entry("JPY", "Yen japonés"),
            Map.entry("CAD", "Dólar canadiense"),
            Map.entry("AUD", "Dólar australiano"),
            Map.entry("BRL", "Real brasileño"),
            Map.entry("ARS", "Peso argentino"),
            Map.entry("CLP", "Peso chileno"),
            Map.entry("COP", "Peso colombiano")
    );

    public static String getName(String code) {
        return NAMES.getOrDefault(code, "Moneda desconocida");
    }
}
