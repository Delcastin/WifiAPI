package com.zerobase.wifiapi.service;

import com.zerobase.wifiapi.entity.SearchHistory;
import com.zerobase.wifiapi.repository.SearchHistoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class SearchHistoryImpl implements SearchHistoryService {

    private final SearchHistoryRepository searchHistoryRepository;


    @Override
    public SearchHistory saveSearchHistory(SearchHistory searchHistory) {
        searchHistoryRepository.save(searchHistory);

        long count = searchHistoryRepository.count();
        if (count > 20) {
            List<SearchHistory> oldHistories = searchHistoryRepository.findTop20ByOrderBySearchDateDesc()
                    .stream()
                    .limit((int)(count - 20))
                    .toList();

            searchHistoryRepository.deleteAllInBatch(oldHistories);
        }

        return searchHistory;
    }


    @Override // 최대 20개의 검색 히스토리 보여주기
    public List<SearchHistory> getRecentSearchHistories() {

        return searchHistoryRepository.findTop20ByOrderBySearchDateDesc();
    }
}
