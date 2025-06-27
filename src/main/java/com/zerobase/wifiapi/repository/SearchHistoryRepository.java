package com.zerobase.wifiapi.repository;

import com.zerobase.wifiapi.entity.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;


import java.util.List;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {

    List<SearchHistory> findTop20ByOrderBySearchDateDesc();

    @Modifying
    void deleteAllInBatch(Iterable<SearchHistory> entities);

}
