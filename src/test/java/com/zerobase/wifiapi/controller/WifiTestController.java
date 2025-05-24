package com.zerobase.wifiapi.controller;

import com.zerobase.wifiapi.dto.WifiApiResponse;
import com.zerobase.wifiapi.service.WifiApiClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WifiTestController {

    private final WifiApiClient client;

    public WifiTestController(WifiApiClient client) {
        this.client = client;
    }

    @GetMapping("/wifi/test")
    public List<WifiApiResponse.WifiRecord> test(){
        return client.fetchWifiRecords();
    }
}
