package com.example.crypto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Names {
    public Status status;
    public List<Datum> data;

    @Getter
    @Setter
    public static class Datum {
        public int id;
        public String name;
        public String symbol;
        public String slug;
        public int rank;
        public int is_active;
        public String first_historical_data;
        public String last_historical_data;
    }

    @Getter
    @Setter
    public static class Status {
        public String timestamp;
        public int error_code;
        public String error_message;
        public int elapsed;
        public int credit_count;
    }
}
