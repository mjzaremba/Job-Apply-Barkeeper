package com.pjatk.barkeeper.barbackend.repositories;

import com.pjatk.barkeeper.barbackend.models.IngredientToDrink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientToDrinkRepository extends JpaRepository<IngredientToDrink, Integer> {
    List<IngredientToDrink> findByIngredientsIdIn(List<Integer> ingredientIds);

    List<IngredientToDrink> findByDrinkId(Integer drinkId);

    @Query("SELECT t.drinkId FROM tblIngredientsToDrinks t GROUP by t.drinkId HAVING COUNT(t.ingredientsId) <= :size")
    List<Integer> findDrinkIdWithIngredientQuantity(@Param("size") Long size);
    List<IngredientToDrink> findIngredientToDrinkByDrinkIdIn(List<Integer> drinkIds);

}
