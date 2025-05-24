package com.zerobase.wifiapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WifiApiResponse {

    @JsonProperty("TbPublicWifiInfo")
    private TbPublicWifiInfo tbPublicWifiInfo;



    public List<WifiRecord> getRow(){
        return tbPublicWifiInfo.getRow();
    }

    @Data
    public static class TbPublicWifiInfo {
        private int list_total_count;
        private List<WifiRecord> row;
    }

    @Data
    public static class WifiRecord {

        @JsonProperty("X_SWIFI_MGR_NO")
        private String mgrNo;

        @JsonProperty("X_SWIFI_WRDOFC")
        private String wrdofc; // 자치구

        @JsonProperty("X_SWIFI_MAIN_NM")
        private String wifiName;

        @JsonProperty("X_SWIFI_ADRES1")
        private String addressRoad;

        @JsonProperty("X_SWIFI_ADRES2")
        private String addressDetail;

        @JsonProperty("X_SWIFI_INSTL_FLOOR")
        private String floor;

        @JsonProperty("X_SWIFI_INSTL_TY")
        private String type;

        @JsonProperty("X_SWIFI_INSTL_MBY")
        private String installBy;

        @JsonProperty("X_SWIFI_SVC_SE")
        private String serviceType;

        @JsonProperty("X_SWIFI_CMCWR")
        private String netType;

        @JsonProperty("X_SWIFI_CNSTC_YEAR")
        private String installYear;

        @JsonProperty("X_SWIFI_INOUT_DOOR")
        private String inOut;

        @JsonProperty("X_SWIFI_REMARS3")
        private String installEnv;

        @JsonProperty("WORK_DTTM")
        private String worked_at;

        @JsonProperty("LAT")
        private String lat;

        @JsonProperty("LNT")
        private String lnt;


        public double getLatAsDouble() {
            return Double.parseDouble(lat);
        }

        public double getLntAsDouble() {
            return Double.parseDouble(lnt);
        }

        @Setter
        @Getter
        @JsonIgnoreProperties(ignoreUnknown = true)
        private double distance; // 거리(Km)

    }
}
