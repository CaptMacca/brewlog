package brews.repository;

import brews.domain.Brew;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrewerRepository extends JpaRepository<Brew, Long> {
}
