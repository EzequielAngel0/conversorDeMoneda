// src/main/java/com/example/apiplayground/model/SupportedCodesResponse.java
package com.example.apiplayground.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SupportedCodesResponse {
    public String result;

    // Formato típico: "supported_codes": [["USD","United States Dollar"], ["MXN","Mexican Peso"], ...]
    @SerializedName("supported_codes")
    public List<List<String>> supportedCodes;

    @SerializedName("error-type")
    public String errorType;
}
