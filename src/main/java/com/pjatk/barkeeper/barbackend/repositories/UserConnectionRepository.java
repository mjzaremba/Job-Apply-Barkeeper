package com.pjatk.barkeeper.barbackend.repositories;

import com.pjatk.barkeeper.barbackend.models.UserConnection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserConnectionRepository extends JpaRepository<UserConnection, Integer> {
    Optional<UserConnection> findByMachineUserIdAndAndMachineId(Integer machineUserId, Integer id);
    Optional<UserConnection> findByMachineUserId(Integer machineUserId);
    List<UserConnection> findAllByMachineId(Integer machineId);
    @Transactional
    @Modifying
    int deleteByMachineId(Integer machineId);
}
