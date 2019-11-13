package gestatestados.mberges.tfg.web.rest;

import gestatestados.mberges.tfg.domain.Remitente;
import gestatestados.mberges.tfg.repository.RemitenteRepository;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link gestatestados.mberges.tfg.domain.Remitente}.
 */
@RestController
@RequestMapping("/api")
public class RemitenteResource {

    private final Logger log = LoggerFactory.getLogger(RemitenteResource.class);

    private static final String ENTITY_NAME = "remitente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RemitenteRepository remitenteRepository;

    public RemitenteResource(RemitenteRepository remitenteRepository) {
        this.remitenteRepository = remitenteRepository;
    }

    /**
     * {@code POST  /remitentes} : Create a new remitente.
     *
     * @param remitente the remitente to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new remitente, or with status {@code 400 (Bad Request)} if the remitente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/remitentes")
    public ResponseEntity<Remitente> createRemitente(@RequestBody Remitente remitente) throws URISyntaxException {
        log.debug("REST request to save Remitente : {}", remitente);
        if (remitente.getId() != null) {
            throw new BadRequestAlertException("A new remitente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Remitente result = remitenteRepository.save(remitente);
        return ResponseEntity.created(new URI("/api/remitentes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /remitentes} : Updates an existing remitente.
     *
     * @param remitente the remitente to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated remitente,
     * or with status {@code 400 (Bad Request)} if the remitente is not valid,
     * or with status {@code 500 (Internal Server Error)} if the remitente couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/remitentes")
    public ResponseEntity<Remitente> updateRemitente(@RequestBody Remitente remitente) throws URISyntaxException {
        log.debug("REST request to update Remitente : {}", remitente);
        if (remitente.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Remitente result = remitenteRepository.save(remitente);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, remitente.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /remitentes} : get all the remitentes.
     *

     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of remitentes in body.
     */
    @GetMapping("/remitentes")
    public List<Remitente> getAllRemitentes(@RequestParam(required = false) String filter) {
        if ("atestado-is-null".equals(filter)) {
            log.debug("REST request to get all Remitentes where atestado is null");
            return StreamSupport
                .stream(remitenteRepository.findAll().spliterator(), false)
                .filter(remitente -> remitente.getAtestado() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all Remitentes");
        return remitenteRepository.findAll();
    }

    /**
     * {@code GET  /remitentes/:id} : get the "id" remitente.
     *
     * @param id the id of the remitente to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the remitente, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/remitentes/{id}")
    public ResponseEntity<Remitente> getRemitente(@PathVariable Long id) {
        log.debug("REST request to get Remitente : {}", id);
        Optional<Remitente> remitente = remitenteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(remitente);
    }

    /**
     * {@code DELETE  /remitentes/:id} : delete the "id" remitente.
     *
     * @param id the id of the remitente to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/remitentes/{id}")
    public ResponseEntity<Void> deleteRemitente(@PathVariable Long id) {
        log.debug("REST request to delete Remitente : {}", id);
        remitenteRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
