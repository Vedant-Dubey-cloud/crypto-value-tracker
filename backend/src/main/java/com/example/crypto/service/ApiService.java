package com.example.crypto.service;

import com.example.crypto.response.NameResponse;
import com.example.crypto.response.Names;
import com.example.crypto.response.TopFiveResponse;
import jakarta.annotation.PostConstruct;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiService {
    private static final Log log = LogFactory.getLog(ApiService.class);

    @Autowired
    private RestTemplate restTemplate;

@Value("${CMC_API_KEY}")
private String apiKey;


    private static final String topFiveUrl = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?start=1&limit=5&convert=USD";
    private static final String priceByNameUrl = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest?symbol=sim&CMC_PRO_API_KEY=appp";

    private static final String allCoinsUrl = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/map?CMC_PRO_API_KEY=appp";

    public Object getTopPrice() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-CMC_PRO_API_KEY", apiKey);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<TopFiveResponse> response = restTemplate.exchange(
                topFiveUrl,
                HttpMethod.GET,
                entity,
                TopFiveResponse.class
        );

        return response.getBody();
    }


    public Object getPrice(String name) {
        String symbol = getSymbol(name);
        if (symbol == null) {
            log.error("No symbol found for " + name);
            return "Invalid coin name";
        }
        String key = priceByNameUrl.replace("sim", symbol);
        key=key.replace("appp",apiKey);
        ResponseEntity<NameResponse> response =
                restTemplate.exchange(key, HttpMethod.GET, null, NameResponse.class);
        return response.getBody();
    }



    public Object getAllCoinNames() {
        String s=allCoinsUrl.replace("appp",apiKey);
        ResponseEntity<Names> response = restTemplate.exchange(s, HttpMethod.GET, null, Names.class);
        return response.getBody();
    }


    public String getSymbol(String userInput) {
        try {
            String s=allCoinsUrl.replace("appp",apiKey);
            ResponseEntity<Names> response = restTemplate.exchange(s, HttpMethod.GET, null, Names.class);
            Names names = response.getBody();

            if (names != null && names.data != null) {
                for (Names.Datum coin : names.data) {
                    if (coin.name.equalsIgnoreCase(userInput) || coin.symbol.equalsIgnoreCase(userInput)) {
                        return coin.symbol;
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error fetching symbol for: " + userInput, e);
        }
        return null;
    }


    @PostConstruct
    public void checkApiKeyLoaded() {
        if (apiKey == null || apiKey.isBlank()) {
            log.error("❌ API Key NOT loaded! Please set CMC_API_KEY environment variable.");
        } else {
            log.info("✅ API Key successfully loaded from environment.");
        }
    }




}
