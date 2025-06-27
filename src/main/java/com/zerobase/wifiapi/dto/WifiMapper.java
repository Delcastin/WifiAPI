package com.zerobase.wifiapi.dto;

import com.zerobase.wifiapi.entity.Wifi;

public class WifiMapper {

    public static Wifi toEntity(WifiApiResponse.WifiRecord record){

        if (record.getLat() == null || record.getLat().equalsIgnoreCase("null") ||
                record.getLnt() == null || record.getLnt().equalsIgnoreCase("null")) {
            throw new IllegalArgumentException("위도 또는 경도 값이 잘못되었습니다: lat=" + record.getLat() + ", lnt=" + record.getLnt());
        }

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
}
