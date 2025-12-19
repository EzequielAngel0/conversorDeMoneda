// src/main/java/com/example/apiplayground/model/ExchangeRatesResponse.java
package com.example.apiplayground.model;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class ExchangeRatesResponse {
    public String result;
    public String base_code;
    public Map<String, Double> conversion_rates;

    @SerializedName("error-type")
    public String errorType;
}
