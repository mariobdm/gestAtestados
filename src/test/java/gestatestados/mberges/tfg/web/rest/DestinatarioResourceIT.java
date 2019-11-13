package gestatestados.mberges.tfg.web.rest;

import gestatestados.mberges.tfg.GestAtestadosApp;
import gestatestados.mberges.tfg.domain.Destinatario;
import gestatestados.mberges.tfg.repository.DestinatarioRepository;
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

import gestatestados.mberges.tfg.domain.enumeration.EnumTipoDestinatario;
/**
 * Integration tests for the {@link DestinatarioResource} REST controller.
 */
@SpringBootTest(classes = GestAtestadosApp.class)
public class DestinatarioResourceIT {

    private static final EnumTipoDestinatario DEFAULT_TIPO = EnumTipoDestinatario.AYUNTAMIENTO;
    private static final EnumTipoDestinatario UPDATED_TIPO = EnumTipoDestinatario.GUARDIA_CIVIL;

    private static final String DEFAULT_NOMBRE_DESTINATARIO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_DESTINATARIO = "BBBBBBBBBB";

    private static final String DEFAULT_DESC_DESTINATARIO = "AAAAAAAAAA";
    private static final String UPDATED_DESC_DESTINATARIO = "BBBBBBBBBB";

    @Autowired
    private DestinatarioRepository destinatarioRepository;

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

    private MockMvc restDestinatarioMockMvc;

    private Destinatario destinatario;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DestinatarioResource destinatarioResource = new DestinatarioResource(destinatarioRepository);
        this.restDestinatarioMockMvc = MockMvcBuilders.standaloneSetup(destinatarioResource)
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
    public static Destinatario createEntity(EntityManager em) {
        Destinatario destinatario = new Destinatario()
            .tipo(DEFAULT_TIPO)
            .nombreDestinatario(DEFAULT_NOMBRE_DESTINATARIO)
            .descDestinatario(DEFAULT_DESC_DESTINATARIO);
        return destinatario;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Destinatario createUpdatedEntity(EntityManager em) {
        Destinatario destinatario = new Destinatario()
            .tipo(UPDATED_TIPO)
            .nombreDestinatario(UPDATED_NOMBRE_DESTINATARIO)
            .descDestinatario(UPDATED_DESC_DESTINATARIO);
        return destinatario;
    }

    @BeforeEach
    public void initTest() {
        destinatario = createEntity(em);
    }

    @Test
    @Transactional
    public void createDestinatario() throws Exception {
        int databaseSizeBeforeCreate = destinatarioRepository.findAll().size();

        // Create the Destinatario
        restDestinatarioMockMvc.perform(post("/api/destinatarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(destinatario)))
            .andExpect(status().isCreated());

        // Validate the Destinatario in the database
        List<Destinatario> destinatarioList = destinatarioRepository.findAll();
        assertThat(destinatarioList).hasSize(databaseSizeBeforeCreate + 1);
        Destinatario testDestinatario = destinatarioList.get(destinatarioList.size() - 1);
        assertThat(testDestinatario.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testDestinatario.getNombreDestinatario()).isEqualTo(DEFAULT_NOMBRE_DESTINATARIO);
        assertThat(testDestinatario.getDescDestinatario()).isEqualTo(DEFAULT_DESC_DESTINATARIO);
    }

    @Test
    @Transactional
    public void createDestinatarioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = destinatarioRepository.findAll().size();

        // Create the Destinatario with an existing ID
        destinatario.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDestinatarioMockMvc.perform(post("/api/destinatarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(destinatario)))
            .andExpect(status().isBadRequest());

        // Validate the Destinatario in the database
        List<Destinatario> destinatarioList = destinatarioRepository.findAll();
        assertThat(destinatarioList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDestinatarios() throws Exception {
        // Initialize the database
        destinatarioRepository.saveAndFlush(destinatario);

        // Get all the destinatarioList
        restDestinatarioMockMvc.perform(get("/api/destinatarios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(destinatario.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].nombreDestinatario").value(hasItem(DEFAULT_NOMBRE_DESTINATARIO.toString())))
            .andExpect(jsonPath("$.[*].descDestinatario").value(hasItem(DEFAULT_DESC_DESTINATARIO.toString())));
    }
    
    @Test
    @Transactional
    public void getDestinatario() throws Exception {
        // Initialize the database
        destinatarioRepository.saveAndFlush(destinatario);

        // Get the destinatario
        restDestinatarioMockMvc.perform(get("/api/destinatarios/{id}", destinatario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(destinatario.getId().intValue()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.nombreDestinatario").value(DEFAULT_NOMBRE_DESTINATARIO.toString()))
            .andExpect(jsonPath("$.descDestinatario").value(DEFAULT_DESC_DESTINATARIO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDestinatario() throws Exception {
        // Get the destinatario
        restDestinatarioMockMvc.perform(get("/api/destinatarios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDestinatario() throws Exception {
        // Initialize the database
        destinatarioRepository.saveAndFlush(destinatario);

        int databaseSizeBeforeUpdate = destinatarioRepository.findAll().size();

        // Update the destinatario
        Destinatario updatedDestinatario = destinatarioRepository.findById(destinatario.getId()).get();
        // Disconnect from session so that the updates on updatedDestinatario are not directly saved in db
        em.detach(updatedDestinatario);
        updatedDestinatario
            .tipo(UPDATED_TIPO)
            .nombreDestinatario(UPDATED_NOMBRE_DESTINATARIO)
            .descDestinatario(UPDATED_DESC_DESTINATARIO);

        restDestinatarioMockMvc.perform(put("/api/destinatarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDestinatario)))
            .andExpect(status().isOk());

        // Validate the Destinatario in the database
        List<Destinatario> destinatarioList = destinatarioRepository.findAll();
        assertThat(destinatarioList).hasSize(databaseSizeBeforeUpdate);
        Destinatario testDestinatario = destinatarioList.get(destinatarioList.size() - 1);
        assertThat(testDestinatario.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testDestinatario.getNombreDestinatario()).isEqualTo(UPDATED_NOMBRE_DESTINATARIO);
        assertThat(testDestinatario.getDescDestinatario()).isEqualTo(UPDATED_DESC_DESTINATARIO);
    }

    @Test
    @Transactional
    public void updateNonExistingDestinatario() throws Exception {
        int databaseSizeBeforeUpdate = destinatarioRepository.findAll().size();

        // Create the Destinatario

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDestinatarioMockMvc.perform(put("/api/destinatarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(destinatario)))
            .andExpect(status().isBadRequest());

        // Validate the Destinatario in the database
        List<Destinatario> destinatarioList = destinatarioRepository.findAll();
        assertThat(destinatarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDestinatario() throws Exception {
        // Initialize the database
        destinatarioRepository.saveAndFlush(destinatario);

        int databaseSizeBeforeDelete = destinatarioRepository.findAll().size();

        // Delete the destinatario
        restDestinatarioMockMvc.perform(delete("/api/destinatarios/{id}", destinatario.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Destinatario> destinatarioList = destinatarioRepository.findAll();
        assertThat(destinatarioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Destinatario.class);
        Destinatario destinatario1 = new Destinatario();
        destinatario1.setId(1L);
        Destinatario destinatario2 = new Destinatario();
        destinatario2.setId(destinatario1.getId());
        assertThat(destinatario1).isEqualTo(destinatario2);
        destinatario2.setId(2L);
        assertThat(destinatario1).isNotEqualTo(destinatario2);
        destinatario1.setId(null);
        assertThat(destinatario1).isNotEqualTo(destinatario2);
    }
}
