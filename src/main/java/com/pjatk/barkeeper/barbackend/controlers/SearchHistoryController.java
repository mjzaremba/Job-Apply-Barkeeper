package com.pjatk.barkeeper.barbackend.controlers;

import com.pjatk.barkeeper.barbackend.models.SearchHistory;
import com.pjatk.barkeeper.barbackend.services.SearchHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "search-history")
public class SearchHistoryController {
    private final SearchHistoryService searchHistoryService;

    @Autowired
    public SearchHistoryController(SearchHistoryService searchHistoryService) {
        this.searchHistoryService = searchHistoryService;
    }

    @GetMapping(value = "/user-id/{userId}")
    public List<SearchHistory> getSearchHistoryByUserId(@PathVariable Integer userId) {
        return searchHistoryService.getSearchHistoryByUserId(userId);
    }
}
