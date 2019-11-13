package gestatestados.mberges.tfg.web.rest;

import gestatestados.mberges.tfg.domain.Atestado;
import gestatestados.mberges.tfg.repository.AtestadoRepository;
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
 * REST controller for managing {@link gestatestados.mberges.tfg.domain.Atestado}.
 */
@RestController
@RequestMapping("/api")
public class AtestadoResource {

    private final Logger log = LoggerFactory.getLogger(AtestadoResource.class);

    private static final String ENTITY_NAME = "atestado";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AtestadoRepository atestadoRepository;

    public AtestadoResource(AtestadoRepository atestadoRepository) {
        this.atestadoRepository = atestadoRepository;
    }

    /**
     * {@code POST  /atestados} : Create a new atestado.
     *
     * @param atestado the atestado to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new atestado, or with status {@code 400 (Bad Request)} if the atestado has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/atestados")
    public ResponseEntity<Atestado> createAtestado(@RequestBody Atestado atestado) throws URISyntaxException {
        log.debug("REST request to save Atestado : {}", atestado);
        if (atestado.getId() != null) {
            throw new BadRequestAlertException("A new atestado cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Atestado result = atestadoRepository.save(atestado);
        return ResponseEntity.created(new URI("/api/atestados/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /atestados} : Updates an existing atestado.
     *
     * @param atestado the atestado to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated atestado,
     * or with status {@code 400 (Bad Request)} if the atestado is not valid,
     * or with status {@code 500 (Internal Server Error)} if the atestado couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/atestados")
    public ResponseEntity<Atestado> updateAtestado(@RequestBody Atestado atestado) throws URISyntaxException {
        log.debug("REST request to update Atestado : {}", atestado);
        if (atestado.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Atestado result = atestadoRepository.save(atestado);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, atestado.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /atestados} : get all the atestados.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of atestados in body.
     */
    @GetMapping("/atestados")
    public List<Atestado> getAllAtestados() {
        log.debug("REST request to get all Atestados");
        return atestadoRepository.findAll();
    }

    /**
     * {@code GET  /atestados/:id} : get the "id" atestado.
     *
     * @param id the id of the atestado to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the atestado, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/atestados/{id}")
    public ResponseEntity<Atestado> getAtestado(@PathVariable Long id) {
        log.debug("REST request to get Atestado : {}", id);
        Optional<Atestado> atestado = atestadoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(atestado);
    }

    /**
     * {@code DELETE  /atestados/:id} : delete the "id" atestado.
     *
     * @param id the id of the atestado to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/atestados/{id}")
    public ResponseEntity<Void> deleteAtestado(@PathVariable Long id) {
        log.debug("REST request to delete Atestado : {}", id);
        atestadoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
