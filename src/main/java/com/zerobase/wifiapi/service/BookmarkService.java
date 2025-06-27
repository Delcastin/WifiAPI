package com.zerobase.wifiapi.service;

import com.zerobase.wifiapi.entity.Bookmark;
import com.zerobase.wifiapi.repository.BookmarkRepository;

import java.util.List;

public interface BookmarkService {

    void addBookmark(String mgrNo, String wifiName);

    List<String> getBookmarkGroupNames();
}
