package com.zerobase.wifiapi;

import com.zerobase.wifiapi.dto.WifiApiResponse;
import com.zerobase.wifiapi.entity.Wifi;
import com.zerobase.wifiapi.entity.WifiMapper;
import com.zerobase.wifiapi.repository.WifiRepository;
import com.zerobase.wifiapi.service.WifiApiClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class WifiApiApplicationTests {

    @Autowired
    private WifiApiClient wifiApiClient;
    @Autowired
    private WifiRepository wifiRepository;

    @Test
    void testfetchWifiRecords() {
        List<WifiApiResponse.WifiRecord> records = wifiApiClient.fetchWifiRecords();

        assertFalse(records.isEmpty(), "API에서 데이터를 불러오지 못했습니다.");
        records.forEach(System.out::println);
    }

    @Test
    void testSaveOneWifiRecord() {
        WifiApiResponse.WifiRecord record = wifiApiClient.fetchWifiRecords().getFirst();
        Wifi wifi = WifiMapper.toEntity(record);
        Wifi saved = wifiRepository.save(wifi);

        assertNotNull(saved.getId(), "데이터 저장이 실패했거나 ID가 생성되지 않았습니다.");
        System.out.println("저장된 Wifi: " + saved);
    }

}
