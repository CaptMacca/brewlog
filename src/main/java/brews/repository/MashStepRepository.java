package brews.repository;

import brews.domain.MashStep;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Steve on 29/06/2017.
 */
public interface MashStepRepository extends JpaRepository<MashStep,Long> {
}
