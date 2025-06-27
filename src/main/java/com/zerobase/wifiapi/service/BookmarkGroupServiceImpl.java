package com.zerobase.wifiapi.service;

import com.zerobase.wifiapi.entity.BookmarkGroup;
import com.zerobase.wifiapi.repository.BookmarkGroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookmarkGroupServiceImpl implements BookmarkGroupService {

    private final BookmarkGroupRepository bookmarkGroupRepository;

    public BookmarkGroupServiceImpl(BookmarkGroupRepository bookmarkGroupRepository) {
        this.bookmarkGroupRepository = bookmarkGroupRepository;
    }

    @Override
    public List<BookmarkGroup> getAllBookmarkGroups() {
        return bookmarkGroupRepository.findAll();
    }

    @Override
    public BookmarkGroup getBookmarkGroupById(Long id) {
        return bookmarkGroupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("북마크 그룹이 존재하지 않습니다."));
    }

    @Override
    public BookmarkGroup createBookmarkGroup(BookmarkGroup bookmarkGroup) {
        return bookmarkGroupRepository.save(bookmarkGroup);
    }

    @Override
    public BookmarkGroup updateBookmarkGroup(Long id, BookmarkGroup updatedBookmarkGroup) {

        BookmarkGroup existing = getBookmarkGroupById(id);

        existing.setGroupName(updatedBookmarkGroup.getGroupName());
        existing.setSortOrder(updatedBookmarkGroup.getSortOrder());


        return bookmarkGroupRepository.save(existing);
    }



    @Override
    public void deleteBookmarkGroup(Long id) {
        bookmarkGroupRepository.deleteById(id);
    }
}
