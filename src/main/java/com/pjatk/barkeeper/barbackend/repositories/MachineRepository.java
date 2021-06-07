package com.pjatk.barkeeper.barbackend.repositories;

import com.pjatk.barkeeper.barbackend.models.Machine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface MachineRepository extends JpaRepository<Machine, String> {
    Optional<Machine> findBySerialNumber(String serialNumber);
    List<Machine> findByUserId(Integer userId);
    Optional<Machine> findByUserIdAndSerialNumber(Integer userId, String serialNumber);

    @Transactional
    @Modifying
    @Query("UPDATE tblMachine m SET m.userId = ?1 WHERE m.serialNumber = ?2")
    void updateMachineOwner(int userId, String serialNumber);
}
