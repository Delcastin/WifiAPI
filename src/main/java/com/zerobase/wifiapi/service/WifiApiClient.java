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

import static com.zerobase.wifiapi.dto.WifiMapper.toEntity;

@Service
public class WifiApiClient {

    private final RestTemplate restTemplate;
    private final WifiRepository wifiRepository;

    @Autowired
    public WifiApiClient(RestTemplate restTemplate, WifiRepository wifiRepository) {
        this.restTemplate = restTemplate;
        this.wifiRepository = wifiRepository;
    }

    public List<WifiApiResponse.WifiRecord> fetchWifiRecords(int start, int end) {
        String url = String.format(
                "http://openapi.seoul.go.kr:8088/496b6650726c656a34347871717242/json/TbPublicWifiInfo/%d/%d/",
                start, end
        );

        try {
            ResponseEntity<WifiApiResponse> response = restTemplate.getForEntity(url, WifiApiResponse.class);
            return Objects.requireNonNull(response.getBody()).getRow();
        } catch (Exception e) {
            throw new RuntimeException("공공 와이파이 API 호출 실패 (start=" + start + ", end=" + end + ")", e);
        }
    }

    public List<WifiApiResponse.WifiRecord> fetchNearByWifi(double lat, double lnt) {
        try {
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
        int totalCount = 0;
        int batchSize = 1000;
        int start = 1;
        int maxLimit = 30000;

        while (start <= maxLimit) {
            int end = Math.min(start + batchSize - 1, maxLimit);

            List<WifiApiResponse.WifiRecord> records;
            try {
                records = fetchWifiRecords(start, end);
            } catch (RuntimeException e) {
                System.out.println("API 요청 실패: start=" + start + ", end=" + end);
                break;
            }

            if (records == null || records.isEmpty()) {
                break; // 더 이상 데이터 없음
            }

            for (WifiApiResponse.WifiRecord record : records) {
                if (saveWifiRecord(record)) {
                    totalCount++;
                }
            }

            start += batchSize;
        }

        return totalCount;
    }

    private boolean saveWifiRecord(WifiApiResponse.WifiRecord record) {
        try {
            if (wifiRepository.existsByMgrNo(record.getMgrNo())) {
                return false;
            }

            Wifi wifi = toEntity(record);
            System.out.println("저장 시도 중: " + wifi.getMgrNo());

            wifiRepository.save(wifi);
            System.out.println("저장 성공: " + wifi.getMgrNo());

            return true;
        } catch (Exception e) {
            System.err.println("저장 실패: " + record.getMgrNo());
            e.printStackTrace();  // 반드시 콘솔에서 확인
            return false;
        }
    }


    private Wifi intoEntity(WifiApiResponse.WifiRecord record) {
        try {
            return toEntity(record);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("잘못된 위도/경도 형식: " + record.getLat() + ", " + record.getLnt());
        }
    }
}

