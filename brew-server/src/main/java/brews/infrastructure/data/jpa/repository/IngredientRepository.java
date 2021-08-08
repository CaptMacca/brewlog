package brews.infrastructure.data.jpa.repository;

import brews.domain.Ingredient;
import brews.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    List<Ingredient> findIngredientsByRecipe(Recipe recipe);
}
