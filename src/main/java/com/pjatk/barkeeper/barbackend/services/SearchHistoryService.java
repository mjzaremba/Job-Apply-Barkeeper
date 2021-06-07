package com.pjatk.barkeeper.barbackend.services;

import com.pjatk.barkeeper.barbackend.models.SearchHistory;
import com.pjatk.barkeeper.barbackend.repositories.SearchHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchHistoryService {
    private final SearchHistoryRepository searchHistoryRepository;

    @Autowired
    public SearchHistoryService(SearchHistoryRepository searchHistoryRepository) {
        this.searchHistoryRepository = searchHistoryRepository;
    }

    public List<SearchHistory> getSearchHistoryByUserId(Integer userId) {
        return searchHistoryRepository.getSearchHistoriesByUserId(userId);
    }
}
