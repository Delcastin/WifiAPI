package com.zerobase.wifiapi.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Wifi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 고유 ID

    private String mgrNo;  // 관리번호
    private String wrdofc; // 자치구
    private String wifiName; // 와이파이명
    private String addressRoad; // 도로명주소
    private String addressDetail; // 상세주소
    private String floor;  // 설치위치 (층 수)
    private String type;   // 설치 유형
    private String installBy; // 설치 기관
    private String serviceType; // 서비스구분
    private String netType; // 망 종류
    private String installYear; // 설치년도
    private String inOut; // 실내외 구분
    private String installEnv; // 설치 환경
    private double lat; // 위도
    private double lnt; // 경도
    private String worked_at; // 작업일자



}
