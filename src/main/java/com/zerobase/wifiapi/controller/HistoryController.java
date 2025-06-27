package com.zerobase.wifiapi.controller;



import com.zerobase.wifiapi.entity.SearchHistory;
import com.zerobase.wifiapi.repository.SearchHistoryRepository;
import com.zerobase.wifiapi.service.SearchHistoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/search")
public class HistoryController {

    private final SearchHistoryService searchHistoryService;
    private final SearchHistoryRepository searchHistoryRepository;

    public HistoryController(SearchHistoryService searchHistoryService, SearchHistoryRepository searchHistoryRepository) {
        this.searchHistoryService = searchHistoryService;
        this.searchHistoryRepository = searchHistoryRepository;
    }

    @GetMapping("/history")
    public String showHistory(Model model) {
        List<SearchHistory> recentHistory = searchHistoryService.getRecentSearchHistories();
        model.addAttribute("recentHistory", recentHistory);
        return "history";
    }

    @PostMapping("/history/delete/{id}")
    public String deleteHistory(@PathVariable("id") Long id) {
        searchHistoryRepository.deleteById(id);

        return "redirect:/search/history";
    }


}
