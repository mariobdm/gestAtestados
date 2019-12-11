package gestatestados.mberges.tfg.repository;
import gestatestados.mberges.tfg.domain.Implicado;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import java.util.Optional;

/**
 * Spring Data  repository for the Implicado entity.
 */
@SuppressWarnings("unused")
@Repository
//public interface ImplicadoRepository extends JpaRepository<Implicado, Long> {
//}
public interface ImplicadoRepository extends JpaRepository<Implicado, Long> {
    @Query("from Implicado d where d.atestado.id=:id")
    List<Implicado> findImplicadosByAtestado(@Param ("id") Long id);
}

