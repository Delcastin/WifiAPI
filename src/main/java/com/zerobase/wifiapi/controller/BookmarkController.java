package com.zerobase.wifiapi.controller;


import com.zerobase.wifiapi.entity.Bookmark;
import com.zerobase.wifiapi.entity.BookmarkGroup;
import com.zerobase.wifiapi.entity.Wifi;
import com.zerobase.wifiapi.repository.BookmarkGroupRepository;
import com.zerobase.wifiapi.repository.BookmarkRepository;
import com.zerobase.wifiapi.repository.WifiRepository;
import com.zerobase.wifiapi.service.BookmarkGroupService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/bookmark")
public class BookmarkController {

    private final BookmarkRepository bookmarkRepository;
    private final BookmarkGroupService bookmarkGroupService;
    private final WifiRepository wifiRepository;
    private final BookmarkGroupRepository bookmarkGroupRepository;

    public BookmarkController(BookmarkRepository bookmarkRepository, BookmarkGroupRepository bookmarkGroupRepository, BookmarkGroupService bookmarkGroupService, WifiRepository wifiRepository, BookmarkGroupRepository bookmarkGroupRepository1) {
        this.bookmarkRepository = bookmarkRepository;
        this.bookmarkGroupService = bookmarkGroupService;
        this.wifiRepository = wifiRepository;
        this.bookmarkGroupRepository = bookmarkGroupRepository;
    }

    @PostMapping("/add")
    public String addBookmark(@RequestParam("bookmarkGroupId") Long bookmarkGroupId,
                              @RequestParam("wifiMgrNo") String wifiMgrNo,
                              @RequestParam("wifiName") String wifiName) {
        BookmarkGroup group = bookmarkGroupRepository.findById(bookmarkGroupId)
                .orElseThrow(() -> new IllegalArgumentException("그룹이 존재하지 않습니다."));
        Wifi wifi = wifiRepository.findByMgrNo(wifiMgrNo)
                .orElseThrow(() -> new IllegalArgumentException("와이파이가 존재하지 않습니다."));

        Bookmark bookmark = new Bookmark();
        bookmark.setBookmarkName(wifiName);
        bookmark.setWifi(wifi);
        bookmark.setBookmarkGroup(group);

        bookmarkRepository.save(bookmark);

        return "redirect:/bookmark/list";
    }




    @GetMapping("/list") // 북마크 리스트 보기
    public String list(Model model) {
        List<Bookmark> bookmarks = bookmarkRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        model.addAttribute("bookmarks", bookmarks);
        return "bookmark-list";
    }

    @GetMapping("/delete/{id}") // 북마크 삭제 Form 띄우기
    public String confirmDelete(@PathVariable("id") Long id, Model model) {
        Bookmark bookmark = bookmarkRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 북마크가 없습니다."));
        model.addAttribute("bookmark", bookmark);
        return "deleteForm";  // 확인용 HTML 페이지
    }

    @PostMapping("/delete/{id}") // 북마크 삭제하기
    public String deleteBookmark(@PathVariable("id") Long id) {
        bookmarkRepository.deleteById(id);
        return "redirect:/bookmark/list";
    }


}
