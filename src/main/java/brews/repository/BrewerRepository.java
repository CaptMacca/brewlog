package brews.repository;

import brews.domain.Brew;
import brews.domain.Brewer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrewerRepository extends JpaRepository<Brewer, Long> {

    Brewer findOneByUserid(String userid);
}
