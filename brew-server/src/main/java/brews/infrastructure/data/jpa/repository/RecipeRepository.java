package brews.infrastructure.data.jpa.repository;

import brews.domain.Recipe;
import brews.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    Optional<Recipe> findRecipeByName(String name);

    Optional<Recipe> findRecipeByNameAndUser(String name, User user);

    List<Recipe> findRecipesByUserUsername(String username);

    List<Recipe> findTop5RatedByUserUsernameOrderByNameDesc(String username);
}
