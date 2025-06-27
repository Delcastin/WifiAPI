package com.zerobase.wifiapi.service;

import com.zerobase.wifiapi.entity.Bookmark;
import com.zerobase.wifiapi.repository.BookmarkRepository;
import com.zerobase.wifiapi.repository.WifiRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookmarkServiceImpl implements BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final WifiRepository wifiRepository;

    public BookmarkServiceImpl(BookmarkRepository bookmarkRepository, WifiRepository wifiRepository) {
        this.bookmarkRepository = bookmarkRepository;
        this.wifiRepository = wifiRepository;
    }



    @Override
    public void addBookmark(String mgrNo, String wifiName) {
        Bookmark bookmark = new Bookmark();

        bookmark.setCreatedAt(LocalDateTime.now());


        bookmarkRepository.save(bookmark);
    }

    @Override
    public List<String> getBookmarkGroupNames() {
        return bookmarkRepository.findDistinctBookmarkNames();
    }
}
