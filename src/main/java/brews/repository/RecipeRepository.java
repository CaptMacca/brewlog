package brews.repository;

import brews.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Steve on 11/06/2017.
 */
public interface RecipeRepository extends JpaRepository<Recipe,Long> {

    List<Recipe> findRecipesByType(String type);
    Recipe findRecipeByName(String name);
 }
