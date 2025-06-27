package com.zerobase.wifiapi.service;


import com.zerobase.wifiapi.dto.WifiApiResponse;
import com.zerobase.wifiapi.entity.Wifi;

import java.util.List;

public interface WifiService {

    // 내 위치를 표시할 위도와 경도 데이터 받아오기
    List<Wifi> getNearbyWifi(double lat, double lnt);

    // 받아온 데이터를 DB에 저장
    List<Wifi> importAllWifiData();

    // 외부 API에서 받아온 원시 데이터를 반환(가공용)
    List<WifiApiResponse.WifiRecord> fetchAllWifiData();

    // 저장된 데이터를 조회
    List<Wifi> getAllWifi();

    // Detail을 표기하기 위해 Wifi관리번호로 찾는다!
    Wifi findByMgrNo(String mgrNo, Double currentLat, Double currentLnt);
}
