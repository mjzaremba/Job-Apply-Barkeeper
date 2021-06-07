package com.pjatk.barkeeper.barbackend.repositories;

import com.pjatk.barkeeper.barbackend.models.order.Order;
import com.pjatk.barkeeper.barbackend.models.order.PreparingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUserId(Integer userId);
    List<Order> findAllByMachineId(Integer machineId);
    List<Order> findAllByMachineIdAndPreparingStatusInOrderByOrderDate(Integer machineId, List<PreparingStatus> preparingStatuses);
    Optional<Order> findTopByPreparingStatusAndMachineIdOrderByOrderDate(PreparingStatus preparingStatus, Integer machineId);
    Optional<Order> findOrderByUserIdAndOrderDateAndPreparingStatus(Integer userId, LocalDateTime orderDate, PreparingStatus preparingStatus);

    @Transactional
    @Modifying
    @Query("UPDATE tblOrders u SET u.preparingStatus = ?1 WHERE u.id = ?2")
    int updatePreparingStatus (PreparingStatus preparingStatus, Integer orderId);

    @Transactional
    @Modifying
    @Query("DELETE FROM tblOrders t WHERE t.machineId = ?1")
    int deleteByMachineId (Integer machineId);

    @Query("SELECT MAX(id) FROM tblOrders")
    Optional<Integer> findMaxOrderId();
}
