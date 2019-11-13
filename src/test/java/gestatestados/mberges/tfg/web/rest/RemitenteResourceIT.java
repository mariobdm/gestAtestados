package gestatestados.mberges.tfg.web.rest;

import gestatestados.mberges.tfg.GestAtestadosApp;
import gestatestados.mberges.tfg.domain.Remitente;
import gestatestados.mberges.tfg.repository.RemitenteRepository;
import gestatestados.mberges.tfg.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static gestatestados.mberges.tfg.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import gestatestados.mberges.tfg.domain.enumeration.EnumTipoRemitente;
/**
 * Integration tests for the {@link RemitenteResource} REST controller.
 */
@SpringBootTest(classes = GestAtestadosApp.class)
public class RemitenteResourceIT {

    private static final EnumTipoRemitente DEFAULT_TIPO = EnumTipoRemitente.POLICIA_LOCAL;
    private static final EnumTipoRemitente UPDATED_TIPO = EnumTipoRemitente.GUARDIA_CIVIL;

    private static final String DEFAULT_NOMBRE_REMITENTE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_REMITENTE = "BBBBBBBBBB";

    private static final String DEFAULT_DESC_REMITENTE = "AAAAAAAAAA";
    private static final String UPDATED_DESC_REMITENTE = "BBBBBBBBBB";

    @Autowired
    private RemitenteRepository remitenteRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restRemitenteMockMvc;

    private Remitente remitente;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RemitenteResource remitenteResource = new RemitenteResource(remitenteRepository);
        this.restRemitenteMockMvc = MockMvcBuilders.standaloneSetup(remitenteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Remitente createEntity(EntityManager em) {
        Remitente remitente = new Remitente()
            .tipo(DEFAULT_TIPO)
            .nombreRemitente(DEFAULT_NOMBRE_REMITENTE)
            .descRemitente(DEFAULT_DESC_REMITENTE);
        return remitente;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Remitente createUpdatedEntity(EntityManager em) {
        Remitente remitente = new Remitente()
            .tipo(UPDATED_TIPO)
            .nombreRemitente(UPDATED_NOMBRE_REMITENTE)
            .descRemitente(UPDATED_DESC_REMITENTE);
        return remitente;
    }

    @BeforeEach
    public void initTest() {
        remitente = createEntity(em);
    }

    @Test
    @Transactional
    public void createRemitente() throws Exception {
        int databaseSizeBeforeCreate = remitenteRepository.findAll().size();

        // Create the Remitente
        restRemitenteMockMvc.perform(post("/api/remitentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(remitente)))
            .andExpect(status().isCreated());

        // Validate the Remitente in the database
        List<Remitente> remitenteList = remitenteRepository.findAll();
        assertThat(remitenteList).hasSize(databaseSizeBeforeCreate + 1);
        Remitente testRemitente = remitenteList.get(remitenteList.size() - 1);
        assertThat(testRemitente.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testRemitente.getNombreRemitente()).isEqualTo(DEFAULT_NOMBRE_REMITENTE);
        assertThat(testRemitente.getDescRemitente()).isEqualTo(DEFAULT_DESC_REMITENTE);
    }

    @Test
    @Transactional
    public void createRemitenteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = remitenteRepository.findAll().size();

        // Create the Remitente with an existing ID
        remitente.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRemitenteMockMvc.perform(post("/api/remitentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(remitente)))
            .andExpect(status().isBadRequest());

        // Validate the Remitente in the database
        List<Remitente> remitenteList = remitenteRepository.findAll();
        assertThat(remitenteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRemitentes() throws Exception {
        // Initialize the database
        remitenteRepository.saveAndFlush(remitente);

        // Get all the remitenteList
        restRemitenteMockMvc.perform(get("/api/remitentes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(remitente.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].nombreRemitente").value(hasItem(DEFAULT_NOMBRE_REMITENTE.toString())))
            .andExpect(jsonPath("$.[*].descRemitente").value(hasItem(DEFAULT_DESC_REMITENTE.toString())));
    }
    
    @Test
    @Transactional
    public void getRemitente() throws Exception {
        // Initialize the database
        remitenteRepository.saveAndFlush(remitente);

        // Get the remitente
        restRemitenteMockMvc.perform(get("/api/remitentes/{id}", remitente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(remitente.getId().intValue()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.nombreRemitente").value(DEFAULT_NOMBRE_REMITENTE.toString()))
            .andExpect(jsonPath("$.descRemitente").value(DEFAULT_DESC_REMITENTE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRemitente() throws Exception {
        // Get the remitente
        restRemitenteMockMvc.perform(get("/api/remitentes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRemitente() throws Exception {
        // Initialize the database
        remitenteRepository.saveAndFlush(remitente);

        int databaseSizeBeforeUpdate = remitenteRepository.findAll().size();

        // Update the remitente
        Remitente updatedRemitente = remitenteRepository.findById(remitente.getId()).get();
        // Disconnect from session so that the updates on updatedRemitente are not directly saved in db
        em.detach(updatedRemitente);
        updatedRemitente
            .tipo(UPDATED_TIPO)
            .nombreRemitente(UPDATED_NOMBRE_REMITENTE)
            .descRemitente(UPDATED_DESC_REMITENTE);

        restRemitenteMockMvc.perform(put("/api/remitentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRemitente)))
            .andExpect(status().isOk());

        // Validate the Remitente in the database
        List<Remitente> remitenteList = remitenteRepository.findAll();
        assertThat(remitenteList).hasSize(databaseSizeBeforeUpdate);
        Remitente testRemitente = remitenteList.get(remitenteList.size() - 1);
        assertThat(testRemitente.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testRemitente.getNombreRemitente()).isEqualTo(UPDATED_NOMBRE_REMITENTE);
        assertThat(testRemitente.getDescRemitente()).isEqualTo(UPDATED_DESC_REMITENTE);
    }

    @Test
    @Transactional
    public void updateNonExistingRemitente() throws Exception {
        int databaseSizeBeforeUpdate = remitenteRepository.findAll().size();

        // Create the Remitente

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRemitenteMockMvc.perform(put("/api/remitentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(remitente)))
            .andExpect(status().isBadRequest());

        // Validate the Remitente in the database
        List<Remitente> remitenteList = remitenteRepository.findAll();
        assertThat(remitenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRemitente() throws Exception {
        // Initialize the database
        remitenteRepository.saveAndFlush(remitente);

        int databaseSizeBeforeDelete = remitenteRepository.findAll().size();

        // Delete the remitente
        restRemitenteMockMvc.perform(delete("/api/remitentes/{id}", remitente.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Remitente> remitenteList = remitenteRepository.findAll();
        assertThat(remitenteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Remitente.class);
        Remitente remitente1 = new Remitente();
        remitente1.setId(1L);
        Remitente remitente2 = new Remitente();
        remitente2.setId(remitente1.getId());
        assertThat(remitente1).isEqualTo(remitente2);
        remitente2.setId(2L);
        assertThat(remitente1).isNotEqualTo(remitente2);
        remitente1.setId(null);
        assertThat(remitente1).isNotEqualTo(remitente2);
    }
}
