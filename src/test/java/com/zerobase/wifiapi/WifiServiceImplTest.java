package com.zerobase.wifiapi;

import com.zerobase.wifiapi.dto.WifiApiResponse;
import com.zerobase.wifiapi.entity.Wifi;
import com.zerobase.wifiapi.repository.WifiRepository;
import com.zerobase.wifiapi.service.WifiApiClient;
import com.zerobase.wifiapi.service.WifiServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WifiServiceImplTest {

    @Mock
    private WifiRepository wifiRepository;

    @Mock
    private WifiApiClient wifiApiClient;

    @InjectMocks
    private WifiServiceImpl wifiService;

    @Test
    void importWifiDataFromOpenApi_shouldReturnSavedCount() {
        // given
        WifiApiResponse.WifiRecord record1 = new WifiApiResponse.WifiRecord();
        record1.setMgrNo("MGR001");
        record1.setWifiName("Test Wifi 1");
        record1.setLat("37.12345");
        record1.setLnt("127.12345");
        // ... 필요한 필드 추가 설정

        WifiApiResponse.WifiRecord record2 = new WifiApiResponse.WifiRecord();
        record2.setMgrNo("MGR002");
        record2.setWifiName("Test Wifi 2");
        record2.setLat("37.54321");
        record2.setLnt("127.54321");

        List<WifiApiResponse.WifiRecord> mockRecords = List.of(record1, record2);
        when(wifiApiClient.fetchWifiRecords()).thenReturn(mockRecords);

        when(wifiRepository.save(any(Wifi.class))).thenReturn(new Wifi()); // save 성공 가정

        // when
        int result = wifiService.importWifiDataFromOpenApi();

        // then
        assertEquals(2, result); // 두 개 모두 저장 성공
        verify(wifiRepository, times(2)).save(any(Wifi.class)); // 저장 메서드가 2번 호출되었는지 확인
    }
}
