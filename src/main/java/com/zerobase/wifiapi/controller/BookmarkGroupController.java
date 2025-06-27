package com.zerobase.wifiapi.controller;

import com.zerobase.wifiapi.entity.BookmarkGroup;
import com.zerobase.wifiapi.repository.BookmarkGroupRepository;
import com.zerobase.wifiapi.service.BookmarkGroupService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/bookmark-group")
public class BookmarkGroupController {

    private final BookmarkGroupRepository bookmarkGroupRepository;
    private final BookmarkGroupService bookmarkGroupService;

    public BookmarkGroupController(BookmarkGroupRepository bookmarkGroupRepository, BookmarkGroupService bookmarkGroupService) {
        this.bookmarkGroupRepository = bookmarkGroupRepository;
        this.bookmarkGroupService = bookmarkGroupService;
    }

    @GetMapping // 북마크 그룹 리스트 조회하기
    public String getBookmarkGroups(Model model) {
        List<BookmarkGroup> groups = bookmarkGroupService.getAllBookmarkGroups();
        List<BookmarkGroup> nonNullIdGroups = groups.stream()
                        .filter(group -> group.getId() != null)
                                .toList();
        model.addAttribute("groups", groups);
        return "bookmark-group-list";
    }

    @PostMapping // 북마크 그룹 추가하기
    public String addBookmarkGroup(@ModelAttribute BookmarkGroup bookmarkGroup) {
        bookmarkGroupService.createBookmarkGroup(bookmarkGroup);
        return "redirect:/bookmark-group";
    }

    @GetMapping("/{id}/edit") // 추가 Form 띄우기
    public String showEditBookmarkGroupForm(@PathVariable("id") Long id, Model model) {
        BookmarkGroup group = bookmarkGroupService.getBookmarkGroupById(id);
        model.addAttribute("groups", group);
        return "editForm"; // 등록, 수정 화면 재사용 가능
    }

    @GetMapping("/{id}/update") // 수정 Form 띄우기
    public String showUpdateBookmarkGroupForm(@PathVariable("id") Long id, Model model) {
        BookmarkGroup group = bookmarkGroupService.getBookmarkGroupById(id);
        model.addAttribute("group", group);
        return "updateForm"; // 등록, 수정 화면 재사용 가능
    }

    @PostMapping("/{id}/edit") // 북마크 그룹 추가하기
    public String addBookmarkGroup(@PathVariable("id") Long id, @ModelAttribute BookmarkGroup bookmarkGroup) {
        bookmarkGroupService.updateBookmarkGroup(id, bookmarkGroup);

        return "redirect:/bookmark-group";
    }

    @PostMapping("/{id}/delete") // 북마크 그룹 삭제하기
    public String deleteBookmarkGroup(@PathVariable("id") Long id) {
        bookmarkGroupService.deleteBookmarkGroup(id);
        return "redirect:/bookmark-group";
    }

    @GetMapping("/edit") // 추가 Form 띄우기
    public String showAddBookmarkGroupForm(Model model) {
        BookmarkGroup group = new BookmarkGroup();
        model.addAttribute("group", group);
        return "editForm";
    }
}
