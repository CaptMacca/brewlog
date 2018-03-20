package brews.repository;

import brews.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    Optional<List<Recipe>> findRecipesByType(String type);

    Optional<Recipe> findRecipeByName(String name);
}
