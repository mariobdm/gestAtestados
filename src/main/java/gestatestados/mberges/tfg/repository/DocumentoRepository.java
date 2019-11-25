package gestatestados.mberges.tfg.repository;
import gestatestados.mberges.tfg.domain.Documento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import java.util.Optional;
/**
 * Spring Data  repository for the Documento entity.
 */
@SuppressWarnings("unused")
@Repository
//public interface DocumentoRepository extends JpaRepository<Documento, Long> {
//}
public interface DocumentoRepository extends JpaRepository<Documento, Long> {
    @Query("from Documento d where d.atestado.id=:id")
    List<Documento> findDocumentosByAtestado(@Param ("id") Long id);
}

