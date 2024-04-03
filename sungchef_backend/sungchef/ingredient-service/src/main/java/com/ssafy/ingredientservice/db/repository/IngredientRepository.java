package com.ssafy.ingredientservice.db.repository;

import com.ssafy.ingredientservice.db.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
    Optional<Ingredient> findIngredientByIngredientId(Integer ingredientId);

    @Query(value =
        "SELECT\n"
            + "* \n"
            + "FROM \n"
            + "ingredient i\n"
            + "WHERE\n"
            + "REPLACE(:ocrText, ' ', '')\n"
            + "LIKE\n"
            + "CONCAT('%', REPLACE(i.ingredient_name, ' ', ''), '%')\n"
            + "ORDER BY i.ingredient_name DESC\n"
            + "LIMIT 1"
        , nativeQuery = true)
    Optional<Ingredient> findIngredientByOCR(@Param("ocrText") String ocrText);
}
