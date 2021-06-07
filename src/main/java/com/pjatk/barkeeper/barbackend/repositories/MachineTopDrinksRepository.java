package com.pjatk.barkeeper.barbackend.repositories;

import com.pjatk.barkeeper.barbackend.models.MachineTopDrinks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface MachineTopDrinksRepository extends JpaRepository<MachineTopDrinks, Integer> {
    List<MachineTopDrinks> findAllByMachineId(Integer MachineId);
    List<MachineTopDrinks> findTop3ByMachineIdOrderByDrinkOrderCountDesc(Integer machineId);
    boolean existsByDrinkIdAndMachineId(Integer drinkId, Integer machineId);
    MachineTopDrinks findMachineTopDrinksByDrinkIdAndMachineId(Integer drinkId, Integer machineId);

    @Transactional
    @Modifying
    int deleteByMachineId(Integer machineId);

    @Transactional
    @Modifying
    @Query("UPDATE tblMachineTopDrinks m SET m.drinkOrderCount = ?1 WHERE m.drinkId = ?2 AND m.machineId = ?3")
    void updateMachineTopDrink(int drinkOrderCount, Integer drinkId, Integer machineId);
}
