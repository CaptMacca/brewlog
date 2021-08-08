package brews.infrastructure.data.jpa.repository;

import brews.domain.Mash;
import brews.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MashRepository extends JpaRepository<Mash, Long> {
    List<Mash> findMashesByRecipe(Recipe recipe);
}
