package brews.repository;

import brews.domain.BrewDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Steve on 11/06/2017.
 */
@Repository
public interface BrewDayRepository extends JpaRepository<BrewDay,Long> {
}
