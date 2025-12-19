// src/main/java/com/example/apiplayground/Main.java
package com.example.apiplayground;

import com.example.apiplayground.api.ExchangeRateApiClient;
import com.example.apiplayground.service.CurrencyConverterService;
import com.example.apiplayground.ui.AppFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        String apiKey = System.getenv("EXCHANGE_RATE_API_KEY");
        if (apiKey == null || apiKey.isBlank()) {
            System.err.println("Falta EXCHANGE_RATE_API_KEY en variables de entorno.");
            System.exit(1);
        }

        var client = new ExchangeRateApiClient(apiKey);
        var service = new CurrencyConverterService(client);

        SwingUtilities.invokeLater(() -> new AppFrame(service).setVisible(true));
    }
}
