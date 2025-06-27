package com.zerobase.wifiapi.repository;

import com.zerobase.wifiapi.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    @Query("SELECT DISTINCT b.bookmarkName FROM Bookmark b")
    List<String> findDistinctBookmarkNames();
}
