package com.zerobase.wifiapi.repository;

import com.zerobase.wifiapi.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
}
