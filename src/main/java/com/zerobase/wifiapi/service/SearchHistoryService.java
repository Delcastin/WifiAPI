package com.zerobase.wifiapi.service;

import com.zerobase.wifiapi.entity.SearchHistory;

import java.util.List;

public interface SearchHistoryService {

    SearchHistory saveSearchHistory(SearchHistory searchHistory);

    List<SearchHistory> getRecentSearchHistories();

}
