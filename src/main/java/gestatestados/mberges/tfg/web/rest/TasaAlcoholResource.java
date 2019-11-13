package gestatestados.mberges.tfg.web.rest;

import gestatestados.mberges.tfg.domain.TasaAlcohol;
import gestatestados.mberges.tfg.repository.TasaAlcoholRepository;
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
 * REST controller for managing {@link gestatestados.mberges.tfg.domain.TasaAlcohol}.
 */
@RestController
@RequestMapping("/api")
public class TasaAlcoholResource {

    private final Logger log = LoggerFactory.getLogger(TasaAlcoholResource.class);

    private static final String ENTITY_NAME = "tasaAlcohol";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TasaAlcoholRepository tasaAlcoholRepository;

    public TasaAlcoholResource(TasaAlcoholRepository tasaAlcoholRepository) {
        this.tasaAlcoholRepository = tasaAlcoholRepository;
    }

    /**
     * {@code POST  /tasa-alcohols} : Create a new tasaAlcohol.
     *
     * @param tasaAlcohol the tasaAlcohol to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tasaAlcohol, or with status {@code 400 (Bad Request)} if the tasaAlcohol has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tasa-alcohols")
    public ResponseEntity<TasaAlcohol> createTasaAlcohol(@RequestBody TasaAlcohol tasaAlcohol) throws URISyntaxException {
        log.debug("REST request to save TasaAlcohol : {}", tasaAlcohol);
        if (tasaAlcohol.getId() != null) {
            throw new BadRequestAlertException("A new tasaAlcohol cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TasaAlcohol result = tasaAlcoholRepository.save(tasaAlcohol);
        return ResponseEntity.created(new URI("/api/tasa-alcohols/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tasa-alcohols} : Updates an existing tasaAlcohol.
     *
     * @param tasaAlcohol the tasaAlcohol to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tasaAlcohol,
     * or with status {@code 400 (Bad Request)} if the tasaAlcohol is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tasaAlcohol couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tasa-alcohols")
    public ResponseEntity<TasaAlcohol> updateTasaAlcohol(@RequestBody TasaAlcohol tasaAlcohol) throws URISyntaxException {
        log.debug("REST request to update TasaAlcohol : {}", tasaAlcohol);
        if (tasaAlcohol.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TasaAlcohol result = tasaAlcoholRepository.save(tasaAlcohol);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tasaAlcohol.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tasa-alcohols} : get all the tasaAlcohols.
     *

     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tasaAlcohols in body.
     */
    @GetMapping("/tasa-alcohols")
    public List<TasaAlcohol> getAllTasaAlcohols(@RequestParam(required = false) String filter) {
        if ("implicado-is-null".equals(filter)) {
            log.debug("REST request to get all TasaAlcohols where implicado is null");
            return StreamSupport
                .stream(tasaAlcoholRepository.findAll().spliterator(), false)
                .filter(tasaAlcohol -> tasaAlcohol.getImplicado() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all TasaAlcohols");
        return tasaAlcoholRepository.findAll();
    }

    /**
     * {@code GET  /tasa-alcohols/:id} : get the "id" tasaAlcohol.
     *
     * @param id the id of the tasaAlcohol to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tasaAlcohol, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tasa-alcohols/{id}")
    public ResponseEntity<TasaAlcohol> getTasaAlcohol(@PathVariable Long id) {
        log.debug("REST request to get TasaAlcohol : {}", id);
        Optional<TasaAlcohol> tasaAlcohol = tasaAlcoholRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tasaAlcohol);
    }

    /**
     * {@code DELETE  /tasa-alcohols/:id} : delete the "id" tasaAlcohol.
     *
     * @param id the id of the tasaAlcohol to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tasa-alcohols/{id}")
    public ResponseEntity<Void> deleteTasaAlcohol(@PathVariable Long id) {
        log.debug("REST request to delete TasaAlcohol : {}", id);
        tasaAlcoholRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
