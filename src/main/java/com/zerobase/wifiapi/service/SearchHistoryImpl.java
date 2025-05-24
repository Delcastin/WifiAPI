package com.zerobase.wifiapi.service;

import com.zerobase.wifiapi.entity.SearchHistory;
import com.zerobase.wifiapi.repository.SearchHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class SearchHistoryImpl implements SearchHistoryService {

    private final SearchHistoryRepository searchHistoryRepository;

    @Override
    public void saveSearchHistory(double lat, double lnt) {
        SearchHistory searchHistory = new SearchHistory();

        searchHistory.setLat(lat);
        searchHistory.setLnt(lnt);
        searchHistory.setSearchDate(LocalDateTime.now());
        searchHistoryRepository.save(searchHistory);
    }

    @Override
    public List<SearchHistory> getAllHistory() {
        return searchHistoryRepository.findAllByOrderBySearchDateDesc();
    }

    @Override
    public void deleteById(Long id) {
        searchHistoryRepository.deleteById(id);
    }
}
