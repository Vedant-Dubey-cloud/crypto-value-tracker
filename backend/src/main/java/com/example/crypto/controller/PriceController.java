package com.example.crypto.controller;

import com.example.crypto.service.ApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/crypto")
@CrossOrigin(origins = "*")
public class PriceController
{
    private static final Logger log = LoggerFactory.getLogger(PriceController.class);
    @Autowired
    private ApiService apiService;
    @GetMapping("/check-key")
    public ResponseEntity<String> checkApiKey() {
        return ResponseEntity.ok("API Key loaded: ✅");
    }

    @GetMapping("/top/coins")
    public ResponseEntity<?> getTopCoins()
    {
        return new ResponseEntity<>(apiService.getTopPrice(),HttpStatus.OK);
    }
    @GetMapping("/{name}")
    public ResponseEntity<?> getPriceByName(@PathVariable String name)
    {
        return new ResponseEntity<>(apiService.getPrice(name),HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity<?> getAll()
    {
       return new ResponseEntity<>(apiService.getAllCoinNames(),HttpStatus.OK);
    }

}
