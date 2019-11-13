package gestatestados.mberges.tfg.web.rest;

import gestatestados.mberges.tfg.GestAtestadosApp;
import gestatestados.mberges.tfg.domain.Atestado;
import gestatestados.mberges.tfg.repository.AtestadoRepository;
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
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static gestatestados.mberges.tfg.web.rest.TestUtil.sameInstant;
import static gestatestados.mberges.tfg.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import gestatestados.mberges.tfg.domain.enumeration.EnumTipoJuicio;
/**
 * Integration tests for the {@link AtestadoResource} REST controller.
 */
@SpringBootTest(classes = GestAtestadosApp.class)
public class AtestadoResourceIT {

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final EnumTipoJuicio DEFAULT_TIPOJUICIO = EnumTipoJuicio.RAPIDO;
    private static final EnumTipoJuicio UPDATED_TIPOJUICIO = EnumTipoJuicio.ORDINARIO;

    private static final LocalDate DEFAULT_FECHA_ATESTADO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_ATESTADO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FECHA_ATESTADO = LocalDate.ofEpochDay(-1L);

    private static final ZonedDateTime DEFAULT_FECHA_HORA_SUCESO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_HORA_SUCESO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_FECHA_HORA_SUCESO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_LUGAR = "AAAAAAAAAA";
    private static final String UPDATED_LUGAR = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACCIDENTE = false;
    private static final Boolean UPDATED_ACCIDENTE = true;

    private static final Boolean DEFAULT_PERMISO = false;
    private static final Boolean UPDATED_PERMISO = true;

    private static final Boolean DEFAULT_ALCOHOLEMIA = false;
    private static final Boolean UPDATED_ALCOHOLEMIA = true;

    private static final Boolean DEFAULT_BIENES = false;
    private static final Boolean UPDATED_BIENES = true;

    private static final Boolean DEFAULT_VELOCIDAD = false;
    private static final Boolean UPDATED_VELOCIDAD = true;

    private static final Boolean DEFAULT_LESIONES = false;
    private static final Boolean UPDATED_LESIONES = true;

    private static final Boolean DEFAULT_FALLECIDO = false;
    private static final Boolean UPDATED_FALLECIDO = true;

    private static final String DEFAULT_INSTRUCTOR = "AAAAAAAAAA";
    private static final String UPDATED_INSTRUCTOR = "BBBBBBBBBB";

    private static final String DEFAULT_SECRETARIO = "AAAAAAAAAA";
    private static final String UPDATED_SECRETARIO = "BBBBBBBBBB";

    @Autowired
    private AtestadoRepository atestadoRepository;

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

    private MockMvc restAtestadoMockMvc;

    private Atestado atestado;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AtestadoResource atestadoResource = new AtestadoResource(atestadoRepository);
        this.restAtestadoMockMvc = MockMvcBuilders.standaloneSetup(atestadoResource)
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
    public static Atestado createEntity(EntityManager em) {
        Atestado atestado = new Atestado()
            .numero(DEFAULT_NUMERO)
            .tipojuicio(DEFAULT_TIPOJUICIO)
            .fechaAtestado(DEFAULT_FECHA_ATESTADO)
            .fechaHoraSuceso(DEFAULT_FECHA_HORA_SUCESO)
            .lugar(DEFAULT_LUGAR)
            .accidente(DEFAULT_ACCIDENTE)
            .permiso(DEFAULT_PERMISO)
            .alcoholemia(DEFAULT_ALCOHOLEMIA)
            .bienes(DEFAULT_BIENES)
            .velocidad(DEFAULT_VELOCIDAD)
            .lesiones(DEFAULT_LESIONES)
            .fallecido(DEFAULT_FALLECIDO)
            .instructor(DEFAULT_INSTRUCTOR)
            .secretario(DEFAULT_SECRETARIO);
        return atestado;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Atestado createUpdatedEntity(EntityManager em) {
        Atestado atestado = new Atestado()
            .numero(UPDATED_NUMERO)
            .tipojuicio(UPDATED_TIPOJUICIO)
            .fechaAtestado(UPDATED_FECHA_ATESTADO)
            .fechaHoraSuceso(UPDATED_FECHA_HORA_SUCESO)
            .lugar(UPDATED_LUGAR)
            .accidente(UPDATED_ACCIDENTE)
            .permiso(UPDATED_PERMISO)
            .alcoholemia(UPDATED_ALCOHOLEMIA)
            .bienes(UPDATED_BIENES)
            .velocidad(UPDATED_VELOCIDAD)
            .lesiones(UPDATED_LESIONES)
            .fallecido(UPDATED_FALLECIDO)
            .instructor(UPDATED_INSTRUCTOR)
            .secretario(UPDATED_SECRETARIO);
        return atestado;
    }

    @BeforeEach
    public void initTest() {
        atestado = createEntity(em);
    }

    @Test
    @Transactional
    public void createAtestado() throws Exception {
        int databaseSizeBeforeCreate = atestadoRepository.findAll().size();

        // Create the Atestado
        restAtestadoMockMvc.perform(post("/api/atestados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(atestado)))
            .andExpect(status().isCreated());

        // Validate the Atestado in the database
        List<Atestado> atestadoList = atestadoRepository.findAll();
        assertThat(atestadoList).hasSize(databaseSizeBeforeCreate + 1);
        Atestado testAtestado = atestadoList.get(atestadoList.size() - 1);
        assertThat(testAtestado.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testAtestado.getTipojuicio()).isEqualTo(DEFAULT_TIPOJUICIO);
        assertThat(testAtestado.getFechaAtestado()).isEqualTo(DEFAULT_FECHA_ATESTADO);
        assertThat(testAtestado.getFechaHoraSuceso()).isEqualTo(DEFAULT_FECHA_HORA_SUCESO);
        assertThat(testAtestado.getLugar()).isEqualTo(DEFAULT_LUGAR);
        assertThat(testAtestado.isAccidente()).isEqualTo(DEFAULT_ACCIDENTE);
        assertThat(testAtestado.isPermiso()).isEqualTo(DEFAULT_PERMISO);
        assertThat(testAtestado.isAlcoholemia()).isEqualTo(DEFAULT_ALCOHOLEMIA);
        assertThat(testAtestado.isBienes()).isEqualTo(DEFAULT_BIENES);
        assertThat(testAtestado.isVelocidad()).isEqualTo(DEFAULT_VELOCIDAD);
        assertThat(testAtestado.isLesiones()).isEqualTo(DEFAULT_LESIONES);
        assertThat(testAtestado.isFallecido()).isEqualTo(DEFAULT_FALLECIDO);
        assertThat(testAtestado.getInstructor()).isEqualTo(DEFAULT_INSTRUCTOR);
        assertThat(testAtestado.getSecretario()).isEqualTo(DEFAULT_SECRETARIO);
    }

    @Test
    @Transactional
    public void createAtestadoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = atestadoRepository.findAll().size();

        // Create the Atestado with an existing ID
        atestado.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAtestadoMockMvc.perform(post("/api/atestados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(atestado)))
            .andExpect(status().isBadRequest());

        // Validate the Atestado in the database
        List<Atestado> atestadoList = atestadoRepository.findAll();
        assertThat(atestadoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAtestados() throws Exception {
        // Initialize the database
        atestadoRepository.saveAndFlush(atestado);

        // Get all the atestadoList
        restAtestadoMockMvc.perform(get("/api/atestados?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(atestado.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.toString())))
            .andExpect(jsonPath("$.[*].tipojuicio").value(hasItem(DEFAULT_TIPOJUICIO.toString())))
            .andExpect(jsonPath("$.[*].fechaAtestado").value(hasItem(DEFAULT_FECHA_ATESTADO.toString())))
            .andExpect(jsonPath("$.[*].fechaHoraSuceso").value(hasItem(sameInstant(DEFAULT_FECHA_HORA_SUCESO))))
            .andExpect(jsonPath("$.[*].lugar").value(hasItem(DEFAULT_LUGAR.toString())))
            .andExpect(jsonPath("$.[*].accidente").value(hasItem(DEFAULT_ACCIDENTE.booleanValue())))
            .andExpect(jsonPath("$.[*].permiso").value(hasItem(DEFAULT_PERMISO.booleanValue())))
            .andExpect(jsonPath("$.[*].alcoholemia").value(hasItem(DEFAULT_ALCOHOLEMIA.booleanValue())))
            .andExpect(jsonPath("$.[*].bienes").value(hasItem(DEFAULT_BIENES.booleanValue())))
            .andExpect(jsonPath("$.[*].velocidad").value(hasItem(DEFAULT_VELOCIDAD.booleanValue())))
            .andExpect(jsonPath("$.[*].lesiones").value(hasItem(DEFAULT_LESIONES.booleanValue())))
            .andExpect(jsonPath("$.[*].fallecido").value(hasItem(DEFAULT_FALLECIDO.booleanValue())))
            .andExpect(jsonPath("$.[*].instructor").value(hasItem(DEFAULT_INSTRUCTOR.toString())))
            .andExpect(jsonPath("$.[*].secretario").value(hasItem(DEFAULT_SECRETARIO.toString())));
    }
    
    @Test
    @Transactional
    public void getAtestado() throws Exception {
        // Initialize the database
        atestadoRepository.saveAndFlush(atestado);

        // Get the atestado
        restAtestadoMockMvc.perform(get("/api/atestados/{id}", atestado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(atestado.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO.toString()))
            .andExpect(jsonPath("$.tipojuicio").value(DEFAULT_TIPOJUICIO.toString()))
            .andExpect(jsonPath("$.fechaAtestado").value(DEFAULT_FECHA_ATESTADO.toString()))
            .andExpect(jsonPath("$.fechaHoraSuceso").value(sameInstant(DEFAULT_FECHA_HORA_SUCESO)))
            .andExpect(jsonPath("$.lugar").value(DEFAULT_LUGAR.toString()))
            .andExpect(jsonPath("$.accidente").value(DEFAULT_ACCIDENTE.booleanValue()))
            .andExpect(jsonPath("$.permiso").value(DEFAULT_PERMISO.booleanValue()))
            .andExpect(jsonPath("$.alcoholemia").value(DEFAULT_ALCOHOLEMIA.booleanValue()))
            .andExpect(jsonPath("$.bienes").value(DEFAULT_BIENES.booleanValue()))
            .andExpect(jsonPath("$.velocidad").value(DEFAULT_VELOCIDAD.booleanValue()))
            .andExpect(jsonPath("$.lesiones").value(DEFAULT_LESIONES.booleanValue()))
            .andExpect(jsonPath("$.fallecido").value(DEFAULT_FALLECIDO.booleanValue()))
            .andExpect(jsonPath("$.instructor").value(DEFAULT_INSTRUCTOR.toString()))
            .andExpect(jsonPath("$.secretario").value(DEFAULT_SECRETARIO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAtestado() throws Exception {
        // Get the atestado
        restAtestadoMockMvc.perform(get("/api/atestados/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAtestado() throws Exception {
        // Initialize the database
        atestadoRepository.saveAndFlush(atestado);

        int databaseSizeBeforeUpdate = atestadoRepository.findAll().size();

        // Update the atestado
        Atestado updatedAtestado = atestadoRepository.findById(atestado.getId()).get();
        // Disconnect from session so that the updates on updatedAtestado are not directly saved in db
        em.detach(updatedAtestado);
        updatedAtestado
            .numero(UPDATED_NUMERO)
            .tipojuicio(UPDATED_TIPOJUICIO)
            .fechaAtestado(UPDATED_FECHA_ATESTADO)
            .fechaHoraSuceso(UPDATED_FECHA_HORA_SUCESO)
            .lugar(UPDATED_LUGAR)
            .accidente(UPDATED_ACCIDENTE)
            .permiso(UPDATED_PERMISO)
            .alcoholemia(UPDATED_ALCOHOLEMIA)
            .bienes(UPDATED_BIENES)
            .velocidad(UPDATED_VELOCIDAD)
            .lesiones(UPDATED_LESIONES)
            .fallecido(UPDATED_FALLECIDO)
            .instructor(UPDATED_INSTRUCTOR)
            .secretario(UPDATED_SECRETARIO);

        restAtestadoMockMvc.perform(put("/api/atestados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAtestado)))
            .andExpect(status().isOk());

        // Validate the Atestado in the database
        List<Atestado> atestadoList = atestadoRepository.findAll();
        assertThat(atestadoList).hasSize(databaseSizeBeforeUpdate);
        Atestado testAtestado = atestadoList.get(atestadoList.size() - 1);
        assertThat(testAtestado.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testAtestado.getTipojuicio()).isEqualTo(UPDATED_TIPOJUICIO);
        assertThat(testAtestado.getFechaAtestado()).isEqualTo(UPDATED_FECHA_ATESTADO);
        assertThat(testAtestado.getFechaHoraSuceso()).isEqualTo(UPDATED_FECHA_HORA_SUCESO);
        assertThat(testAtestado.getLugar()).isEqualTo(UPDATED_LUGAR);
        assertThat(testAtestado.isAccidente()).isEqualTo(UPDATED_ACCIDENTE);
        assertThat(testAtestado.isPermiso()).isEqualTo(UPDATED_PERMISO);
        assertThat(testAtestado.isAlcoholemia()).isEqualTo(UPDATED_ALCOHOLEMIA);
        assertThat(testAtestado.isBienes()).isEqualTo(UPDATED_BIENES);
        assertThat(testAtestado.isVelocidad()).isEqualTo(UPDATED_VELOCIDAD);
        assertThat(testAtestado.isLesiones()).isEqualTo(UPDATED_LESIONES);
        assertThat(testAtestado.isFallecido()).isEqualTo(UPDATED_FALLECIDO);
        assertThat(testAtestado.getInstructor()).isEqualTo(UPDATED_INSTRUCTOR);
        assertThat(testAtestado.getSecretario()).isEqualTo(UPDATED_SECRETARIO);
    }

    @Test
    @Transactional
    public void updateNonExistingAtestado() throws Exception {
        int databaseSizeBeforeUpdate = atestadoRepository.findAll().size();

        // Create the Atestado

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAtestadoMockMvc.perform(put("/api/atestados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(atestado)))
            .andExpect(status().isBadRequest());

        // Validate the Atestado in the database
        List<Atestado> atestadoList = atestadoRepository.findAll();
        assertThat(atestadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAtestado() throws Exception {
        // Initialize the database
        atestadoRepository.saveAndFlush(atestado);

        int databaseSizeBeforeDelete = atestadoRepository.findAll().size();

        // Delete the atestado
        restAtestadoMockMvc.perform(delete("/api/atestados/{id}", atestado.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Atestado> atestadoList = atestadoRepository.findAll();
        assertThat(atestadoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Atestado.class);
        Atestado atestado1 = new Atestado();
        atestado1.setId(1L);
        Atestado atestado2 = new Atestado();
        atestado2.setId(atestado1.getId());
        assertThat(atestado1).isEqualTo(atestado2);
        atestado2.setId(2L);
        assertThat(atestado1).isNotEqualTo(atestado2);
        atestado1.setId(null);
        assertThat(atestado1).isNotEqualTo(atestado2);
    }
}
