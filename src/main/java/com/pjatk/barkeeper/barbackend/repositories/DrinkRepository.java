package com.pjatk.barkeeper.barbackend.repositories;

import com.pjatk.barkeeper.barbackend.models.Drink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DrinkRepository extends JpaRepository<Drink, Integer> {
    Drink findDrinkById(Integer id);
    List<Drink> findDrinkByIdIn(List<Integer> id);
}
