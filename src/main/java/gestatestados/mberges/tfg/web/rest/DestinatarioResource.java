package gestatestados.mberges.tfg.web.rest;

import gestatestados.mberges.tfg.domain.Destinatario;
import gestatestados.mberges.tfg.repository.DestinatarioRepository;
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
 * REST controller for managing {@link gestatestados.mberges.tfg.domain.Destinatario}.
 */
@RestController
@RequestMapping("/api")
public class DestinatarioResource {

    private final Logger log = LoggerFactory.getLogger(DestinatarioResource.class);

    private static final String ENTITY_NAME = "destinatario";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DestinatarioRepository destinatarioRepository;

    public DestinatarioResource(DestinatarioRepository destinatarioRepository) {
        this.destinatarioRepository = destinatarioRepository;
    }

    /**
     * {@code POST  /destinatarios} : Create a new destinatario.
     *
     * @param destinatario the destinatario to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new destinatario, or with status {@code 400 (Bad Request)} if the destinatario has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/destinatarios")
    public ResponseEntity<Destinatario> createDestinatario(@RequestBody Destinatario destinatario) throws URISyntaxException {
        log.debug("REST request to save Destinatario : {}", destinatario);
        if (destinatario.getId() != null) {
            throw new BadRequestAlertException("A new destinatario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Destinatario result = destinatarioRepository.save(destinatario);
        return ResponseEntity.created(new URI("/api/destinatarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /destinatarios} : Updates an existing destinatario.
     *
     * @param destinatario the destinatario to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated destinatario,
     * or with status {@code 400 (Bad Request)} if the destinatario is not valid,
     * or with status {@code 500 (Internal Server Error)} if the destinatario couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/destinatarios")
    public ResponseEntity<Destinatario> updateDestinatario(@RequestBody Destinatario destinatario) throws URISyntaxException {
        log.debug("REST request to update Destinatario : {}", destinatario);
        if (destinatario.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Destinatario result = destinatarioRepository.save(destinatario);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, destinatario.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /destinatarios} : get all the destinatarios.
     *

     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of destinatarios in body.
     */
    @GetMapping("/destinatarios")
    public List<Destinatario> getAllDestinatarios(@RequestParam(required = false) String filter) {
        if ("atestado-is-null".equals(filter)) {
            log.debug("REST request to get all Destinatarios where atestado is null");
            return StreamSupport
                .stream(destinatarioRepository.findAll().spliterator(), false)
                .filter(destinatario -> destinatario.getAtestado() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all Destinatarios");
        return destinatarioRepository.findAll();
    }

    /**
     * {@code GET  /destinatarios/:id} : get the "id" destinatario.
     *
     * @param id the id of the destinatario to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the destinatario, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/destinatarios/{id}")
    public ResponseEntity<Destinatario> getDestinatario(@PathVariable Long id) {
        log.debug("REST request to get Destinatario : {}", id);
        Optional<Destinatario> destinatario = destinatarioRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(destinatario);
    }

    /**
     * {@code DELETE  /destinatarios/:id} : delete the "id" destinatario.
     *
     * @param id the id of the destinatario to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/destinatarios/{id}")
    public ResponseEntity<Void> deleteDestinatario(@PathVariable Long id) {
        log.debug("REST request to delete Destinatario : {}", id);
        destinatarioRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
