package com.zerobase.wifiapi.service;

import com.zerobase.wifiapi.entity.BookmarkGroup;

import java.util.List;

public interface BookmarkGroupService {

    List<BookmarkGroup> getAllBookmarkGroups(); // 북마크 그룹 목록 가져오기

    BookmarkGroup getBookmarkGroupById(Long id); // 북마크 그룹 ID로 조회하기

    BookmarkGroup createBookmarkGroup(BookmarkGroup bookmarkGroup); // 북마크 그룹 생성하기

    BookmarkGroup updateBookmarkGroup(Long id, BookmarkGroup updatedBookmarkGroup); // 북마크 그룹 수정하기

    void deleteBookmarkGroup(Long id); // 북마크 그룹 삭제하기
}
