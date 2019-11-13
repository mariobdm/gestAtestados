package gestatestados.mberges.tfg.web.rest;

import gestatestados.mberges.tfg.GestAtestadosApp;
import gestatestados.mberges.tfg.domain.TasaAlcohol;
import gestatestados.mberges.tfg.repository.TasaAlcoholRepository;
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

/**
 * Integration tests for the {@link TasaAlcoholResource} REST controller.
 */
@SpringBootTest(classes = GestAtestadosApp.class)
public class TasaAlcoholResourceIT {

    private static final Float DEFAULT_TASA_NO_EVIDENCIAL = 1F;
    private static final Float UPDATED_TASA_NO_EVIDENCIAL = 2F;
    private static final Float SMALLER_TASA_NO_EVIDENCIAL = 1F - 1F;

    private static final Float DEFAULT_TASA_EVIDENCIAL_1 = 1F;
    private static final Float UPDATED_TASA_EVIDENCIAL_1 = 2F;
    private static final Float SMALLER_TASA_EVIDENCIAL_1 = 1F - 1F;

    private static final Float DEFAULT_TASA_EVIDENCIAL_2 = 1F;
    private static final Float UPDATED_TASA_EVIDENCIAL_2 = 2F;
    private static final Float SMALLER_TASA_EVIDENCIAL_2 = 1F - 1F;

    private static final Float DEFAULT_TASA_EN_SANGRE = 1F;
    private static final Float UPDATED_TASA_EN_SANGRE = 2F;
    private static final Float SMALLER_TASA_EN_SANGRE = 1F - 1F;

    @Autowired
    private TasaAlcoholRepository tasaAlcoholRepository;

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

    private MockMvc restTasaAlcoholMockMvc;

    private TasaAlcohol tasaAlcohol;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TasaAlcoholResource tasaAlcoholResource = new TasaAlcoholResource(tasaAlcoholRepository);
        this.restTasaAlcoholMockMvc = MockMvcBuilders.standaloneSetup(tasaAlcoholResource)
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
    public static TasaAlcohol createEntity(EntityManager em) {
        TasaAlcohol tasaAlcohol = new TasaAlcohol()
            .tasaNoEvidencial(DEFAULT_TASA_NO_EVIDENCIAL)
            .tasaEvidencial1(DEFAULT_TASA_EVIDENCIAL_1)
            .tasaEvidencial2(DEFAULT_TASA_EVIDENCIAL_2)
            .tasaEnSangre(DEFAULT_TASA_EN_SANGRE);
        return tasaAlcohol;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TasaAlcohol createUpdatedEntity(EntityManager em) {
        TasaAlcohol tasaAlcohol = new TasaAlcohol()
            .tasaNoEvidencial(UPDATED_TASA_NO_EVIDENCIAL)
            .tasaEvidencial1(UPDATED_TASA_EVIDENCIAL_1)
            .tasaEvidencial2(UPDATED_TASA_EVIDENCIAL_2)
            .tasaEnSangre(UPDATED_TASA_EN_SANGRE);
        return tasaAlcohol;
    }

    @BeforeEach
    public void initTest() {
        tasaAlcohol = createEntity(em);
    }

    @Test
    @Transactional
    public void createTasaAlcohol() throws Exception {
        int databaseSizeBeforeCreate = tasaAlcoholRepository.findAll().size();

        // Create the TasaAlcohol
        restTasaAlcoholMockMvc.perform(post("/api/tasa-alcohols")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tasaAlcohol)))
            .andExpect(status().isCreated());

        // Validate the TasaAlcohol in the database
        List<TasaAlcohol> tasaAlcoholList = tasaAlcoholRepository.findAll();
        assertThat(tasaAlcoholList).hasSize(databaseSizeBeforeCreate + 1);
        TasaAlcohol testTasaAlcohol = tasaAlcoholList.get(tasaAlcoholList.size() - 1);
        assertThat(testTasaAlcohol.getTasaNoEvidencial()).isEqualTo(DEFAULT_TASA_NO_EVIDENCIAL);
        assertThat(testTasaAlcohol.getTasaEvidencial1()).isEqualTo(DEFAULT_TASA_EVIDENCIAL_1);
        assertThat(testTasaAlcohol.getTasaEvidencial2()).isEqualTo(DEFAULT_TASA_EVIDENCIAL_2);
        assertThat(testTasaAlcohol.getTasaEnSangre()).isEqualTo(DEFAULT_TASA_EN_SANGRE);
    }

    @Test
    @Transactional
    public void createTasaAlcoholWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tasaAlcoholRepository.findAll().size();

        // Create the TasaAlcohol with an existing ID
        tasaAlcohol.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTasaAlcoholMockMvc.perform(post("/api/tasa-alcohols")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tasaAlcohol)))
            .andExpect(status().isBadRequest());

        // Validate the TasaAlcohol in the database
        List<TasaAlcohol> tasaAlcoholList = tasaAlcoholRepository.findAll();
        assertThat(tasaAlcoholList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTasaAlcohols() throws Exception {
        // Initialize the database
        tasaAlcoholRepository.saveAndFlush(tasaAlcohol);

        // Get all the tasaAlcoholList
        restTasaAlcoholMockMvc.perform(get("/api/tasa-alcohols?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tasaAlcohol.getId().intValue())))
            .andExpect(jsonPath("$.[*].tasaNoEvidencial").value(hasItem(DEFAULT_TASA_NO_EVIDENCIAL.doubleValue())))
            .andExpect(jsonPath("$.[*].tasaEvidencial1").value(hasItem(DEFAULT_TASA_EVIDENCIAL_1.doubleValue())))
            .andExpect(jsonPath("$.[*].tasaEvidencial2").value(hasItem(DEFAULT_TASA_EVIDENCIAL_2.doubleValue())))
            .andExpect(jsonPath("$.[*].tasaEnSangre").value(hasItem(DEFAULT_TASA_EN_SANGRE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getTasaAlcohol() throws Exception {
        // Initialize the database
        tasaAlcoholRepository.saveAndFlush(tasaAlcohol);

        // Get the tasaAlcohol
        restTasaAlcoholMockMvc.perform(get("/api/tasa-alcohols/{id}", tasaAlcohol.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tasaAlcohol.getId().intValue()))
            .andExpect(jsonPath("$.tasaNoEvidencial").value(DEFAULT_TASA_NO_EVIDENCIAL.doubleValue()))
            .andExpect(jsonPath("$.tasaEvidencial1").value(DEFAULT_TASA_EVIDENCIAL_1.doubleValue()))
            .andExpect(jsonPath("$.tasaEvidencial2").value(DEFAULT_TASA_EVIDENCIAL_2.doubleValue()))
            .andExpect(jsonPath("$.tasaEnSangre").value(DEFAULT_TASA_EN_SANGRE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTasaAlcohol() throws Exception {
        // Get the tasaAlcohol
        restTasaAlcoholMockMvc.perform(get("/api/tasa-alcohols/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTasaAlcohol() throws Exception {
        // Initialize the database
        tasaAlcoholRepository.saveAndFlush(tasaAlcohol);

        int databaseSizeBeforeUpdate = tasaAlcoholRepository.findAll().size();

        // Update the tasaAlcohol
        TasaAlcohol updatedTasaAlcohol = tasaAlcoholRepository.findById(tasaAlcohol.getId()).get();
        // Disconnect from session so that the updates on updatedTasaAlcohol are not directly saved in db
        em.detach(updatedTasaAlcohol);
        updatedTasaAlcohol
            .tasaNoEvidencial(UPDATED_TASA_NO_EVIDENCIAL)
            .tasaEvidencial1(UPDATED_TASA_EVIDENCIAL_1)
            .tasaEvidencial2(UPDATED_TASA_EVIDENCIAL_2)
            .tasaEnSangre(UPDATED_TASA_EN_SANGRE);

        restTasaAlcoholMockMvc.perform(put("/api/tasa-alcohols")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTasaAlcohol)))
            .andExpect(status().isOk());

        // Validate the TasaAlcohol in the database
        List<TasaAlcohol> tasaAlcoholList = tasaAlcoholRepository.findAll();
        assertThat(tasaAlcoholList).hasSize(databaseSizeBeforeUpdate);
        TasaAlcohol testTasaAlcohol = tasaAlcoholList.get(tasaAlcoholList.size() - 1);
        assertThat(testTasaAlcohol.getTasaNoEvidencial()).isEqualTo(UPDATED_TASA_NO_EVIDENCIAL);
        assertThat(testTasaAlcohol.getTasaEvidencial1()).isEqualTo(UPDATED_TASA_EVIDENCIAL_1);
        assertThat(testTasaAlcohol.getTasaEvidencial2()).isEqualTo(UPDATED_TASA_EVIDENCIAL_2);
        assertThat(testTasaAlcohol.getTasaEnSangre()).isEqualTo(UPDATED_TASA_EN_SANGRE);
    }

    @Test
    @Transactional
    public void updateNonExistingTasaAlcohol() throws Exception {
        int databaseSizeBeforeUpdate = tasaAlcoholRepository.findAll().size();

        // Create the TasaAlcohol

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTasaAlcoholMockMvc.perform(put("/api/tasa-alcohols")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tasaAlcohol)))
            .andExpect(status().isBadRequest());

        // Validate the TasaAlcohol in the database
        List<TasaAlcohol> tasaAlcoholList = tasaAlcoholRepository.findAll();
        assertThat(tasaAlcoholList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTasaAlcohol() throws Exception {
        // Initialize the database
        tasaAlcoholRepository.saveAndFlush(tasaAlcohol);

        int databaseSizeBeforeDelete = tasaAlcoholRepository.findAll().size();

        // Delete the tasaAlcohol
        restTasaAlcoholMockMvc.perform(delete("/api/tasa-alcohols/{id}", tasaAlcohol.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TasaAlcohol> tasaAlcoholList = tasaAlcoholRepository.findAll();
        assertThat(tasaAlcoholList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TasaAlcohol.class);
        TasaAlcohol tasaAlcohol1 = new TasaAlcohol();
        tasaAlcohol1.setId(1L);
        TasaAlcohol tasaAlcohol2 = new TasaAlcohol();
        tasaAlcohol2.setId(tasaAlcohol1.getId());
        assertThat(tasaAlcohol1).isEqualTo(tasaAlcohol2);
        tasaAlcohol2.setId(2L);
        assertThat(tasaAlcohol1).isNotEqualTo(tasaAlcohol2);
        tasaAlcohol1.setId(null);
        assertThat(tasaAlcohol1).isNotEqualTo(tasaAlcohol2);
    }
}
