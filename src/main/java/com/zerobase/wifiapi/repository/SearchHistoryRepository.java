package com.zerobase.wifiapi.repository;

import com.zerobase.wifiapi.entity.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {

    List<SearchHistory> findAllByOrderBySearchDateDesc();
}
