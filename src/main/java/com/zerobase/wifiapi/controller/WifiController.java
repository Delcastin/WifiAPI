package com.zerobase.wifiapi.controller;


import com.zerobase.wifiapi.dto.WifiApiResponse;
import com.zerobase.wifiapi.repository.WifiRepository;
import com.zerobase.wifiapi.service.WifiApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wifi")
public class WifiController {

    private final WifiRepository wifiRepository;
    private final WifiApiClient wifiApiClient;

    @Autowired
    public WifiController(WifiRepository wifiRepository, WifiApiClient wifiApiClient) {
        this.wifiRepository = wifiRepository;
        this.wifiApiClient = wifiApiClient;
    }

}
