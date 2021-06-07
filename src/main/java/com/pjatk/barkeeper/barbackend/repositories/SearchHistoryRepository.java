package com.pjatk.barkeeper.barbackend.repositories;

import com.pjatk.barkeeper.barbackend.models.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Integer> {
    List<SearchHistory> getSearchHistoriesByUserId(Integer userId);
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO tbl_search_history (user_id, drink_id, search_date) VALUES (?1, ?2, now());", nativeQuery = true)
    void insertSearchHistory(Integer userId, Integer drinkId);

}
