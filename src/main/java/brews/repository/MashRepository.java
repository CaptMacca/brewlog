package brews.repository;

import brews.domain.Mash;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by Steve on 29/06/2017.
 */
@Repository
@Transactional
public interface MashRepository extends JpaRepository<Mash,Long> {
}
