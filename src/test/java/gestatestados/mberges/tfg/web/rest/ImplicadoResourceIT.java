package gestatestados.mberges.tfg.web.rest;

import gestatestados.mberges.tfg.GestAtestadosApp;
import gestatestados.mberges.tfg.domain.Implicado;
import gestatestados.mberges.tfg.repository.ImplicadoRepository;
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
import java.time.ZoneId;
import java.util.List;

import static gestatestados.mberges.tfg.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import gestatestados.mberges.tfg.domain.enumeration.EnumTipoDocumentacion;
import gestatestados.mberges.tfg.domain.enumeration.EnumTipoImplicado;
/**
 * Integration tests for the {@link ImplicadoResource} REST controller.
 */
@SpringBootTest(classes = GestAtestadosApp.class)
public class ImplicadoResourceIT {

    private static final EnumTipoDocumentacion DEFAULT_TIPO_DOCUMENTO = EnumTipoDocumentacion.DNI;
    private static final EnumTipoDocumentacion UPDATED_TIPO_DOCUMENTO = EnumTipoDocumentacion.NIE;

    private static final String DEFAULT_DOCUMENTO = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO_1 = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO_1 = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO_2 = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO_2 = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_NACIMIENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_NACIMIENTO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FECHA_NACIMIENTO = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final EnumTipoImplicado DEFAULT_CALIDAD = EnumTipoImplicado.IMPUTADO;
    private static final EnumTipoImplicado UPDATED_CALIDAD = EnumTipoImplicado.DETENIDO;

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    private static final String DEFAULT_MUNICIPIO = "AAAAAAAAAA";
    private static final String UPDATED_MUNICIPIO = "BBBBBBBBBB";

    private static final Integer DEFAULT_CODIGOPOSTAL = 1;
    private static final Integer UPDATED_CODIGOPOSTAL = 2;
    private static final Integer SMALLER_CODIGOPOSTAL = 1 - 1;

    @Autowired
    private ImplicadoRepository implicadoRepository;

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

    private MockMvc restImplicadoMockMvc;

    private Implicado implicado;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ImplicadoResource implicadoResource = new ImplicadoResource(implicadoRepository);
        this.restImplicadoMockMvc = MockMvcBuilders.standaloneSetup(implicadoResource)
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
    public static Implicado createEntity(EntityManager em) {
        Implicado implicado = new Implicado()
            .tipoDocumento(DEFAULT_TIPO_DOCUMENTO)
            .documento(DEFAULT_DOCUMENTO)
            .nombre(DEFAULT_NOMBRE)
            .apellido1(DEFAULT_APELLIDO_1)
            .apellido2(DEFAULT_APELLIDO_2)
            .fechaNacimiento(DEFAULT_FECHA_NACIMIENTO)
            .telefono(DEFAULT_TELEFONO)
            .calidad(DEFAULT_CALIDAD)
            .direccion(DEFAULT_DIRECCION)
            .municipio(DEFAULT_MUNICIPIO)
            .codigopostal(DEFAULT_CODIGOPOSTAL);
        return implicado;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Implicado createUpdatedEntity(EntityManager em) {
        Implicado implicado = new Implicado()
            .tipoDocumento(UPDATED_TIPO_DOCUMENTO)
            .documento(UPDATED_DOCUMENTO)
            .nombre(UPDATED_NOMBRE)
            .apellido1(UPDATED_APELLIDO_1)
            .apellido2(UPDATED_APELLIDO_2)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .telefono(UPDATED_TELEFONO)
            .calidad(UPDATED_CALIDAD)
            .direccion(UPDATED_DIRECCION)
            .municipio(UPDATED_MUNICIPIO)
            .codigopostal(UPDATED_CODIGOPOSTAL);
        return implicado;
    }

    @BeforeEach
    public void initTest() {
        implicado = createEntity(em);
    }

    @Test
    @Transactional
    public void createImplicado() throws Exception {
        int databaseSizeBeforeCreate = implicadoRepository.findAll().size();

        // Create the Implicado
        restImplicadoMockMvc.perform(post("/api/implicados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(implicado)))
            .andExpect(status().isCreated());

        // Validate the Implicado in the database
        List<Implicado> implicadoList = implicadoRepository.findAll();
        assertThat(implicadoList).hasSize(databaseSizeBeforeCreate + 1);
        Implicado testImplicado = implicadoList.get(implicadoList.size() - 1);
        assertThat(testImplicado.getTipoDocumento()).isEqualTo(DEFAULT_TIPO_DOCUMENTO);
        assertThat(testImplicado.getDocumento()).isEqualTo(DEFAULT_DOCUMENTO);
        assertThat(testImplicado.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testImplicado.getApellido1()).isEqualTo(DEFAULT_APELLIDO_1);
        assertThat(testImplicado.getApellido2()).isEqualTo(DEFAULT_APELLIDO_2);
        assertThat(testImplicado.getFechaNacimiento()).isEqualTo(DEFAULT_FECHA_NACIMIENTO);
        assertThat(testImplicado.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testImplicado.getCalidad()).isEqualTo(DEFAULT_CALIDAD);
        assertThat(testImplicado.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testImplicado.getMunicipio()).isEqualTo(DEFAULT_MUNICIPIO);
        assertThat(testImplicado.getCodigopostal()).isEqualTo(DEFAULT_CODIGOPOSTAL);
    }

    @Test
    @Transactional
    public void createImplicadoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = implicadoRepository.findAll().size();

        // Create the Implicado with an existing ID
        implicado.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restImplicadoMockMvc.perform(post("/api/implicados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(implicado)))
            .andExpect(status().isBadRequest());

        // Validate the Implicado in the database
        List<Implicado> implicadoList = implicadoRepository.findAll();
        assertThat(implicadoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllImplicados() throws Exception {
        // Initialize the database
        implicadoRepository.saveAndFlush(implicado);

        // Get all the implicadoList
        restImplicadoMockMvc.perform(get("/api/implicados?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(implicado.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoDocumento").value(hasItem(DEFAULT_TIPO_DOCUMENTO.toString())))
            .andExpect(jsonPath("$.[*].documento").value(hasItem(DEFAULT_DOCUMENTO.toString())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].apellido1").value(hasItem(DEFAULT_APELLIDO_1.toString())))
            .andExpect(jsonPath("$.[*].apellido2").value(hasItem(DEFAULT_APELLIDO_2.toString())))
            .andExpect(jsonPath("$.[*].fechaNacimiento").value(hasItem(DEFAULT_FECHA_NACIMIENTO.toString())))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO.toString())))
            .andExpect(jsonPath("$.[*].calidad").value(hasItem(DEFAULT_CALIDAD.toString())))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION.toString())))
            .andExpect(jsonPath("$.[*].municipio").value(hasItem(DEFAULT_MUNICIPIO.toString())))
            .andExpect(jsonPath("$.[*].codigopostal").value(hasItem(DEFAULT_CODIGOPOSTAL)));
    }
    
    @Test
    @Transactional
    public void getImplicado() throws Exception {
        // Initialize the database
        implicadoRepository.saveAndFlush(implicado);

        // Get the implicado
        restImplicadoMockMvc.perform(get("/api/implicados/{id}", implicado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(implicado.getId().intValue()))
            .andExpect(jsonPath("$.tipoDocumento").value(DEFAULT_TIPO_DOCUMENTO.toString()))
            .andExpect(jsonPath("$.documento").value(DEFAULT_DOCUMENTO.toString()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.apellido1").value(DEFAULT_APELLIDO_1.toString()))
            .andExpect(jsonPath("$.apellido2").value(DEFAULT_APELLIDO_2.toString()))
            .andExpect(jsonPath("$.fechaNacimiento").value(DEFAULT_FECHA_NACIMIENTO.toString()))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO.toString()))
            .andExpect(jsonPath("$.calidad").value(DEFAULT_CALIDAD.toString()))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION.toString()))
            .andExpect(jsonPath("$.municipio").value(DEFAULT_MUNICIPIO.toString()))
            .andExpect(jsonPath("$.codigopostal").value(DEFAULT_CODIGOPOSTAL));
    }

    @Test
    @Transactional
    public void getNonExistingImplicado() throws Exception {
        // Get the implicado
        restImplicadoMockMvc.perform(get("/api/implicados/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateImplicado() throws Exception {
        // Initialize the database
        implicadoRepository.saveAndFlush(implicado);

        int databaseSizeBeforeUpdate = implicadoRepository.findAll().size();

        // Update the implicado
        Implicado updatedImplicado = implicadoRepository.findById(implicado.getId()).get();
        // Disconnect from session so that the updates on updatedImplicado are not directly saved in db
        em.detach(updatedImplicado);
        updatedImplicado
            .tipoDocumento(UPDATED_TIPO_DOCUMENTO)
            .documento(UPDATED_DOCUMENTO)
            .nombre(UPDATED_NOMBRE)
            .apellido1(UPDATED_APELLIDO_1)
            .apellido2(UPDATED_APELLIDO_2)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .telefono(UPDATED_TELEFONO)
            .calidad(UPDATED_CALIDAD)
            .direccion(UPDATED_DIRECCION)
            .municipio(UPDATED_MUNICIPIO)
            .codigopostal(UPDATED_CODIGOPOSTAL);

        restImplicadoMockMvc.perform(put("/api/implicados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedImplicado)))
            .andExpect(status().isOk());

        // Validate the Implicado in the database
        List<Implicado> implicadoList = implicadoRepository.findAll();
        assertThat(implicadoList).hasSize(databaseSizeBeforeUpdate);
        Implicado testImplicado = implicadoList.get(implicadoList.size() - 1);
        assertThat(testImplicado.getTipoDocumento()).isEqualTo(UPDATED_TIPO_DOCUMENTO);
        assertThat(testImplicado.getDocumento()).isEqualTo(UPDATED_DOCUMENTO);
        assertThat(testImplicado.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testImplicado.getApellido1()).isEqualTo(UPDATED_APELLIDO_1);
        assertThat(testImplicado.getApellido2()).isEqualTo(UPDATED_APELLIDO_2);
        assertThat(testImplicado.getFechaNacimiento()).isEqualTo(UPDATED_FECHA_NACIMIENTO);
        assertThat(testImplicado.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testImplicado.getCalidad()).isEqualTo(UPDATED_CALIDAD);
        assertThat(testImplicado.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testImplicado.getMunicipio()).isEqualTo(UPDATED_MUNICIPIO);
        assertThat(testImplicado.getCodigopostal()).isEqualTo(UPDATED_CODIGOPOSTAL);
    }

    @Test
    @Transactional
    public void updateNonExistingImplicado() throws Exception {
        int databaseSizeBeforeUpdate = implicadoRepository.findAll().size();

        // Create the Implicado

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImplicadoMockMvc.perform(put("/api/implicados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(implicado)))
            .andExpect(status().isBadRequest());

        // Validate the Implicado in the database
        List<Implicado> implicadoList = implicadoRepository.findAll();
        assertThat(implicadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteImplicado() throws Exception {
        // Initialize the database
        implicadoRepository.saveAndFlush(implicado);

        int databaseSizeBeforeDelete = implicadoRepository.findAll().size();

        // Delete the implicado
        restImplicadoMockMvc.perform(delete("/api/implicados/{id}", implicado.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Implicado> implicadoList = implicadoRepository.findAll();
        assertThat(implicadoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Implicado.class);
        Implicado implicado1 = new Implicado();
        implicado1.setId(1L);
        Implicado implicado2 = new Implicado();
        implicado2.setId(implicado1.getId());
        assertThat(implicado1).isEqualTo(implicado2);
        implicado2.setId(2L);
        assertThat(implicado1).isNotEqualTo(implicado2);
        implicado1.setId(null);
        assertThat(implicado1).isNotEqualTo(implicado2);
    }
}
