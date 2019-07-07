package brews.repository;

import brews.domain.Brew;
import brews.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrewsRepository extends JpaRepository<Brew, Long> {

    List<Brew> findBrewsByRecipe(Recipe recipe);
    List<Brew> findBrewsByUserUsername(String username);
}
