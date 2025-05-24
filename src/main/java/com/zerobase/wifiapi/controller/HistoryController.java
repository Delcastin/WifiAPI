package com.zerobase.wifiapi.controller;


import com.zerobase.wifiapi.entity.SearchHistory;
import com.zerobase.wifiapi.service.SearchHistoryService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@Getter
@Setter
@RequestMapping("/wifi")
public class HistoryController {

    private final SearchHistoryService searchHistoryService;

    public HistoryController(SearchHistoryService searchHistoryService) {
        this.searchHistoryService = searchHistoryService;
    }

    @GetMapping("/history")
    public String showHistoryList(Model model) {
        List<SearchHistory> histories = searchHistoryService.getAllHistory();
        model.addAttribute("histories", histories);
        return "history";
    }

    @PostMapping("/delete/{id}")
    public String deleteHistory(@PathVariable Long id) {
        searchHistoryService.deleteById(id);

        return "redirect:/history";
    }
}
