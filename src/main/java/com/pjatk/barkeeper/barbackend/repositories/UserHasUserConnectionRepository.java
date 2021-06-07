package com.pjatk.barkeeper.barbackend.repositories;

import com.pjatk.barkeeper.barbackend.models.UserHasUserConnection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserHasUserConnectionRepository extends JpaRepository<UserHasUserConnection, Integer> {
    List<UserHasUserConnection> findByUserId(Integer userId);
}
