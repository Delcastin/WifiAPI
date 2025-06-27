package com.zerobase.wifiapi.repository;

import com.zerobase.wifiapi.entity.BookmarkGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookmarkGroupRepository extends JpaRepository<BookmarkGroup, Long> {

    Optional<BookmarkGroup> findById(Long id);

    Optional<BookmarkGroup> findByGroupName(String bookmarkGroupName);
}
