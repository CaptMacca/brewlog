package brews.repository;

import brews.domain.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Steve on 29/06/2017.
 */
public interface IngredientRepository extends JpaRepository<Ingredient,Long> {
}
