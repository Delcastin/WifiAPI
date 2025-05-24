package com.zerobase.wifiapi.repository;

import com.zerobase.wifiapi.entity.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {

}
