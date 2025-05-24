package com.zerobase.wifiapi.service;

import com.zerobase.wifiapi.dto.WifiApiResponse;
import com.zerobase.wifiapi.entity.Wifi;
import com.zerobase.wifiapi.repository.WifiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
public class WifiApiClient {

    private final RestTemplate restTemplate;
    private final WifiRepository wifiRepository;

    @Autowired
    public WifiApiClient(RestTemplate restTemplate, WifiRepository wifiRepository) {
        this.restTemplate = restTemplate;
        this.wifiRepository = wifiRepository;
    }

    public List<WifiApiResponse.WifiRecord> fetchWifiRecords() {
        String url = "http://openapi.seoul.go.kr:8088/496b6650726c656a34347871717242/json/TbPublicWifiInfo/1/5/";

        try {
            // RestTemplate을 사용하여 XML 응답을 처리
            ResponseEntity<WifiApiResponse> response = restTemplate.getForEntity(url, WifiApiResponse.class);
            return Objects.requireNonNull(response.getBody()).getRow();
        } catch (Exception e) {
            throw new RuntimeException("공공 와이파이 API 호출을 실패했습니다.", e);
        }
    }

    public List<WifiApiResponse.WifiRecord> fetchNearByWifi(double lat, double lnt){
        try{
            String url = "http://openapi.seoul.go.kr:8088/496b6650726c656a34347871717242/json/TbPublicWifiInfo/1/1000/";
            WifiApiResponse response = restTemplate.getForObject(url, WifiApiResponse.class);

            // TODO : 거리 계산 후 lat, lnt 기준으로 가까운 것만 추려서 반환한다.

            return Objects.requireNonNull(response).getRow().stream()
                    .filter(record ->
                            record.getLat() != null && !record.getLat().equals("null") &&
                                    record.getLnt() != null && !record.getLnt().equals("null")
                    )
                    .sorted(Comparator.comparingDouble(record ->
                            GetUtils.getDistance(lat, lnt,
                                    Double.parseDouble(record.getLat()),
                                    Double.parseDouble(record.getLnt()))
                    ))
                    .limit(20)
                    .toList();


        } catch (Exception e) {
            throw new RuntimeException("공공 와이파이 API 호출에 실패하셨습니다", e);
        }
    }

    public int importWifiDataFromOpenApi() {
        List<WifiApiResponse.WifiRecord> wifiRecords = fetchWifiRecords();

        int count = 0;
        for(WifiApiResponse.WifiRecord record : wifiRecords){
            boolean saved = saveWifiRecord(record);
            if(saved){
                count++;
            }
        }
        return count;
    }

    private boolean saveWifiRecord(WifiApiResponse.WifiRecord record) {
        try{
            Wifi wifi = toEntity(record);
            wifiRepository.save(wifi);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private Wifi toEntity(WifiApiResponse.WifiRecord record) {

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

        return wifi;
    }
}

