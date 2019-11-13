package gestatestados.mberges.tfg.repository;
import gestatestados.mberges.tfg.domain.Implicado;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Implicado entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImplicadoRepository extends JpaRepository<Implicado, Long> {

}
