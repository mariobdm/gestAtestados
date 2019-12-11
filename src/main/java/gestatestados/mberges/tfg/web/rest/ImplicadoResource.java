package gestatestados.mberges.tfg.web.rest;

import gestatestados.mberges.tfg.domain.Implicado;
import gestatestados.mberges.tfg.repository.ImplicadoRepository;
import gestatestados.mberges.tfg.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link gestatestados.mberges.tfg.domain.Implicado}.
 */
@RestController
@RequestMapping("/api")
public class ImplicadoResource {

    private final Logger log = LoggerFactory.getLogger(ImplicadoResource.class);

    private static final String ENTITY_NAME = "implicado";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ImplicadoRepository implicadoRepository;

    public ImplicadoResource(ImplicadoRepository implicadoRepository) {
        this.implicadoRepository = implicadoRepository;
    }

    /**
     * {@code POST  /implicados} : Create a new implicado.
     *
     * @param implicado the implicado to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new implicado, or with status {@code 400 (Bad Request)} if the implicado has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/implicados")
    public ResponseEntity<Implicado> createImplicado(@RequestBody Implicado implicado) throws URISyntaxException {
        log.debug("REST request to save Implicado : {}", implicado);
        if (implicado.getId() != null) {
            throw new BadRequestAlertException("A new implicado cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Implicado result = implicadoRepository.save(implicado);
        return ResponseEntity.created(new URI("/api/implicados/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /implicados} : Updates an existing implicado.
     *
     * @param implicado the implicado to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated implicado,
     * or with status {@code 400 (Bad Request)} if the implicado is not valid,
     * or with status {@code 500 (Internal Server Error)} if the implicado couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/implicados")
    public ResponseEntity<Implicado> updateImplicado(@RequestBody Implicado implicado) throws URISyntaxException {
        log.debug("REST request to update Implicado : {}", implicado);
        if (implicado.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Implicado result = implicadoRepository.save(implicado);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, implicado.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /implicados} : get all the implicados.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of implicados in body.
     */
    @GetMapping("/implicados")
    public List<Implicado> getAllImplicados() {
        log.debug("REST request to get all Implicados");
        return implicadoRepository.findAll();
    }

    /**
     * {@code GET  /implicados/:id} : get the "id" implicado.
     *
     * @param id the id of the implicado to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the implicado, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/implicados/{id}")
    public ResponseEntity<Implicado> getImplicado(@PathVariable Long id) {
        log.debug("REST request to get Implicado : {}", id);
        Optional<Implicado> implicado = implicadoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(implicado);
    }

    /**
     * {@code GET  implicados/atestado/:id} : get documents by atestado id.
     *
     * @param id the id atestado
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the documento, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/implicados/atestado/{id}")
    public List<Implicado> getImplicadosByAtestado(@PathVariable Long id) {
        log.debug("REST request to get Implicados by Atestado : {}", id);
        List<Implicado> implicados = implicadoRepository.findImplicadosByAtestado(id);
        return implicados;
    }

    /**
     * {@code DELETE  /implicados/:id} : delete the "id" implicado.
     *
     * @param id the id of the implicado to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/implicados/{id}")
    public ResponseEntity<Void> deleteImplicado(@PathVariable Long id) {
        log.debug("REST request to delete Implicado : {}", id);
        implicadoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
