// src/main/java/com/example/apiplayground/model/CurrencyItem.java
package com.example.apiplayground.model;

public class CurrencyItem {
    private final String code;
    private final String name;

    public CurrencyItem(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String code() {
        return code;
    }

    public String name() {
        return name;
    }

    @Override
    public String toString() {
        return name + " (" + code + ")";
    }
}
