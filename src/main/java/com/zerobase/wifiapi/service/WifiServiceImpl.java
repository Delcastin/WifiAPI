package com.zerobase.wifiapi.service;

import com.zerobase.wifiapi.dto.WifiApiResponse;
import com.zerobase.wifiapi.entity.Wifi;
import com.zerobase.wifiapi.repository.WifiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.zerobase.wifiapi.dto.WifiMapper.toEntity;

@Service
@RequiredArgsConstructor
public class WifiServiceImpl implements WifiService{

    private final RestTemplate restTemplate;

    private static final String API_URL = "http://openapi.seoul.go.kr:8088/496b6650726c656a34347871717242/json/TbPublicWifiInfo/1/1000/";
    private final WifiRepository wifiRepository;


    @Override
    public List<Wifi> getNearbyWifi(double lat, double lnt) {
        List<Wifi> wifiList = wifiRepository.findAll();

        for(Wifi wifi : wifiList){
            double distance = GetUtils.getDistance(lat, lnt, wifi.getLat(), wifi.getLnt());
            wifi.setDistance(distance);
        }

        return wifiList.stream()
                .sorted(Comparator.comparing(Wifi::getDistance))
                .collect(Collectors.toList());
    }

    @Override
    public List<Wifi> importAllWifiData() {

        try {
            WifiApiResponse response = restTemplate.getForObject(API_URL, WifiApiResponse.class);
            if(response == null || response.getRow() == null || response.getRow().isEmpty()){
                throw new RuntimeException("OPEN API 응답이 비어있습니다.");
            }

            return response.getRow().stream()
                    .map(this::convertToWifi)
                    .collect(Collectors.toList());
        } catch(Exception e){
            throw new RuntimeException("공공 Wifi Data 가져오는 중 오류가 발생했습니다.", e);
        }

    }

    private Wifi convertToWifi(WifiApiResponse.WifiRecord record) {

        return toEntity(record);
    }

    @Override
    public List<WifiApiResponse.WifiRecord> fetchAllWifiData() {
        return List.of();
    }

    @Override
    public List<Wifi> getAllWifi() {
        return List.of();
    }

    @Override
    public Wifi findByMgrNo(String mgrNo, Double currentLat, Double currentLnt) {
        Wifi wifi = wifiRepository.findByMgrNo(mgrNo)
                .orElseThrow(() -> new IllegalArgumentException("Not Found"));

        if (currentLat != null && currentLnt != null) {
            double distance = GetUtils.getDistance(currentLat, currentLnt, wifi.getLat(), wifi.getLnt());
            wifi.setDistance(distance);
        } else {
            wifi.setDistance(null); // 또는 -1 등
        }

        return wifi;
    }
}
