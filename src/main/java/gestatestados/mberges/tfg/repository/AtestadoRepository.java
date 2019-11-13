package gestatestados.mberges.tfg.repository;
import gestatestados.mberges.tfg.domain.Atestado;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Atestado entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AtestadoRepository extends JpaRepository<Atestado, Long> {

}
