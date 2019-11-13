package gestatestados.mberges.tfg.repository;
import gestatestados.mberges.tfg.domain.Remitente;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Remitente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RemitenteRepository extends JpaRepository<Remitente, Long> {

}
