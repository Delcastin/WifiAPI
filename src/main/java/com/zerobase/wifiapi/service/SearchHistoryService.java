package com.zerobase.wifiapi.service;

import com.zerobase.wifiapi.entity.SearchHistory;

import java.util.List;

public interface SearchHistoryService {

    void saveSearchHistory(double lat, double lnt);

    List<SearchHistory> getAllHistory();

    void deleteById(Long id);
}
