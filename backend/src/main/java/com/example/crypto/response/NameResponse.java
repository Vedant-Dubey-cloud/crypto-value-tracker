package com.example.crypto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class NameResponse {

    private Status status;

    // "data" is a map of symbol -> CryptoData
    private Map<String, CryptoData> data;

    @Getter
    @Setter
    public static class Status {
        private String timestamp;
        private int error_code;
        private String error_message;
    }

    @Getter
    @Setter
    public static class CryptoData {
        private String name;
        private String symbol;
        private Quote quote;
    }

    @Getter
    @Setter
    public static class Quote {
        @JsonProperty("USD")
        private USD usd;
    }

    @Getter
    @Setter
    public static class USD {
        private double price;
    }
}
