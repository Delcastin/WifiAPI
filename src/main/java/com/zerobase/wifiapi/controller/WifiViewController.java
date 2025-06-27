package com.zerobase.wifiapi.controller;

import com.zerobase.wifiapi.dto.WifiApiResponse;
import com.zerobase.wifiapi.entity.SearchHistory;
import com.zerobase.wifiapi.entity.Wifi;
import com.zerobase.wifiapi.repository.BookmarkGroupRepository;
import com.zerobase.wifiapi.service.GetUtils;
import com.zerobase.wifiapi.service.SearchHistoryService;
import com.zerobase.wifiapi.service.WifiApiClient;
import com.zerobase.wifiapi.service.WifiService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
@Getter
@Setter
@AllArgsConstructor
public class WifiViewController {

    private final WifiApiClient wifiApiClient;
    private final BookmarkGroupRepository bookmarkGroupRepository;

    private final SearchHistoryService searchHistoryService;
    private final WifiService wifiService;

    @GetMapping("/wifi/nearby")
    public String showNearbyWifi(@RequestParam(name = "lat", required = false) Double lat,
                                 @RequestParam(name = "lnt", required = false) Double lnt,
                                 Model model) {

        SearchHistory history = new SearchHistory();
        history.setLat(lat);
        history.setLnt(lnt);
        history.setSearchDate(LocalDateTime.now());

        searchHistoryService.saveSearchHistory(history);

        List<WifiApiResponse.WifiRecord> wifiList = new ArrayList<>();

        if (lat != null && lnt != null) {
            wifiList = new ArrayList<>(wifiApiClient.fetchNearByWifi(lat, lnt));  // 불변 리스트 복사

            for (WifiApiResponse.WifiRecord record : wifiList) {
                double distance = GetUtils.getDistance(lat, lnt, record.getLatAsDouble(), record.getLntAsDouble());
                record.setDistance(Math.round(distance * 10000.0) / 10000.0);
            }

            wifiList.sort(Comparator.comparingDouble(WifiApiResponse.WifiRecord::getDistance));
        }

        model.addAttribute("wifiList", wifiList);
        return "wifi-list";
    }

    @GetMapping("/wifi/import")
    public String importWifiData(Model model) {
        int savedCount = wifiApiClient.importWifiDataFromOpenApi();
        System.out.println("savedCount = " + savedCount);  // 로그 확인용
        model.addAttribute("count", savedCount);
        return "import";
    }

    @GetMapping("/wifi/detail/{mgrNo}")
    public String showWifiDetail(@PathVariable("mgrNo") String mgrNo, Model model,
                                 @RequestParam(name = "lat", required = false) Double lat,
                                 @RequestParam(name = "lnt", required = false) Double lnt) {
        if (lat != null && lat.isNaN() || lnt != null && lnt.isNaN()) {
            throw new RuntimeException("lat/lnt is NaN");
        }

        Wifi wifi = wifiService.findByMgrNo(mgrNo, lat, lnt);

        model.addAttribute("wifi", wifi);
        model.addAttribute("lat", lat);
        model.addAttribute("lnt", lnt);
        model.addAttribute("groups", bookmarkGroupRepository.findAll());

        return "wifi-detail";
    }
}
