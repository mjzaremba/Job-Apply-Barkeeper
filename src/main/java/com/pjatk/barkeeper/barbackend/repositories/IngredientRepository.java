package com.pjatk.barkeeper.barbackend.repositories;

import com.pjatk.barkeeper.barbackend.models.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
    Ingredient findIngredientByName(String name);
    Ingredient findIngredientById(Integer id);
    boolean existsById(Integer id);
}
