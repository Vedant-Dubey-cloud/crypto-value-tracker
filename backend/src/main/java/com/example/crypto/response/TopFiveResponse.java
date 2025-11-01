package com.example.crypto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import java.util.List;

@Getter
@Setter

public class TopFiveResponse {

    @JsonProperty("data")
    private List<Datum> data;

    @Getter
    @Setter
    public static class Datum {
        private String name;
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
