package brews.repository;

import brews.domain.Mash;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Steve on 29/06/2017.
 */
public interface MashRepository extends JpaRepository<Mash,Long> {
}
