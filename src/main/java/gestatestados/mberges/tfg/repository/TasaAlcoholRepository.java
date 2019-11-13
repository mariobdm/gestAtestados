package gestatestados.mberges.tfg.repository;
import gestatestados.mberges.tfg.domain.TasaAlcohol;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TasaAlcohol entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TasaAlcoholRepository extends JpaRepository<TasaAlcohol, Long> {

}
