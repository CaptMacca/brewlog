package brews.repository;

import brews.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Steve on 11/06/2017.
 */
@Repository
@Transactional
public interface RecipeRepository extends JpaRepository<Recipe,Long> {

    public List<Recipe> findRecipesByType(String type);
    public Recipe findRecipeByName(String name);
 }
