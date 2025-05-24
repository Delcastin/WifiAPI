package com.zerobase.wifiapi.controller;

import com.zerobase.wifiapi.dto.WifiApiResponse;
import com.zerobase.wifiapi.service.WifiApiClient;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Profile("test")
@RestController
public class WifiTestController {

    private final WifiApiClient client;

    public WifiTestController(WifiApiClient client) {
        this.client = client;
    }


}
