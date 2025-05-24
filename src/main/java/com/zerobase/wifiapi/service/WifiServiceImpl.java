package com.zerobase.wifiapi.service;

import com.zerobase.wifiapi.dto.WifiApiResponse;
import com.zerobase.wifiapi.entity.Wifi;
import com.zerobase.wifiapi.repository.WifiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WifiServiceImpl implements WifiService{

    private final RestTemplate restTemplate;

    private final WifiRepository wifiRepository;
    private final WifiApiClient wifiApiClient;



    private static final String API_URL = "http://openapi.seoul.go.kr:8088/496b6650726c656a34347871717242/json/TbPublicWifiInfo/1/1000/";


    @Override
    public List<Wifi> getNearbyWifi(double lat, double lnt) {
        return List.of();
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
            throw new RuntimeException("공공 Wifi Data를 가져오는 중 오류가 발생했습니다.", e);
        }

    }

    private Wifi convertToWifi(WifiApiResponse.WifiRecord record) {

        return Wifi.builder()
                .mgrNo(record.getMgrNo())
                .wrdofc(record.getWrdofc())
                .wifiName(record.getWifiName())
                .addressRoad(record.getAddressRoad())
                .addressDetail(record.getAddressDetail())
                .floor(record.getFloor())
                .type(record.getType())
                .installBy(record.getInstallBy())
                .serviceType(record.getServiceType())
                .netType(record.getNetType())
                .installYear(record.getInstallYear())
                .inOut(record.getInOut())
                .installEnv(record.getInstallEnv())
                .worked_at(record.getWorked_at())
                .lat(Double.parseDouble(record.getLat()))
                .lnt(Double.parseDouble(record.getLnt()))
                .build();
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
    public int importWifiDataFromOpenApi(){
        List<WifiApiResponse.WifiRecord> records = wifiApiClient.fetchWifiRecords();

        int saved = 0;
        for(WifiApiResponse.WifiRecord record : records){
            if(saveWifiRecord(record)){
                saved++;
            }
        }
        return saved;
    }

    private boolean saveWifiRecord(WifiApiResponse.WifiRecord record) {
        try{
            Wifi wifi = new Wifi();
            wifi.setMgrNo(record.getMgrNo());
            wifi.setWrdofc(record.getWrdofc());
            wifi.setWifiName(record.getWifiName());
            wifi.setAddressRoad(record.getAddressRoad());
            wifi.setAddressDetail(record.getAddressDetail());
            wifi.setType(record.getType());
            wifi.setInstallBy(record.getInstallBy());
            wifi.setServiceType(record.getServiceType());
            wifi.setNetType(record.getNetType());
            wifi.setInstallYear(record.getInstallYear());
            wifi.setInOut(record.getInOut());
            wifi.setInstallEnv(record.getInstallEnv());
            wifi.setLat(record.getLatAsDouble());
            wifi.setLnt(record.getLntAsDouble());
            wifi.setWorked_at(record.getWorked_at());

            wifiRepository.save(wifi);
        } catch(Exception e){
            return false;
        }
        return true;
    }


}
