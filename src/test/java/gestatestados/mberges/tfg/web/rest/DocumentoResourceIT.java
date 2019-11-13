package gestatestados.mberges.tfg.web.rest;

import gestatestados.mberges.tfg.GestAtestadosApp;
import gestatestados.mberges.tfg.domain.Documento;
import gestatestados.mberges.tfg.repository.DocumentoRepository;
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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static gestatestados.mberges.tfg.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DocumentoResource} REST controller.
 */
@SpringBootTest(classes = GestAtestadosApp.class)
public class DocumentoResourceIT {

    private static final String DEFAULT_NOMBRE_DOC = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_DOC = "BBBBBBBBBB";

    private static final String DEFAULT_DESC_DOC = "AAAAAAAAAA";
    private static final String UPDATED_DESC_DOC = "BBBBBBBBBB";

    private static final byte[] DEFAULT_ARCHIVO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ARCHIVO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ARCHIVO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ARCHIVO_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_ARCHIVO_PDF = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ARCHIVO_PDF = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ARCHIVO_PDF_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ARCHIVO_PDF_CONTENT_TYPE = "image/png";

    @Autowired
    private DocumentoRepository documentoRepository;

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

    private MockMvc restDocumentoMockMvc;

    private Documento documento;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DocumentoResource documentoResource = new DocumentoResource(documentoRepository);
        this.restDocumentoMockMvc = MockMvcBuilders.standaloneSetup(documentoResource)
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
    public static Documento createEntity(EntityManager em) {
        Documento documento = new Documento()
            .nombreDoc(DEFAULT_NOMBRE_DOC)
            .descDoc(DEFAULT_DESC_DOC)
            .archivo(DEFAULT_ARCHIVO)
            .archivoContentType(DEFAULT_ARCHIVO_CONTENT_TYPE)
            .archivoPdf(DEFAULT_ARCHIVO_PDF)
            .archivoPdfContentType(DEFAULT_ARCHIVO_PDF_CONTENT_TYPE);
        return documento;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Documento createUpdatedEntity(EntityManager em) {
        Documento documento = new Documento()
            .nombreDoc(UPDATED_NOMBRE_DOC)
            .descDoc(UPDATED_DESC_DOC)
            .archivo(UPDATED_ARCHIVO)
            .archivoContentType(UPDATED_ARCHIVO_CONTENT_TYPE)
            .archivoPdf(UPDATED_ARCHIVO_PDF)
            .archivoPdfContentType(UPDATED_ARCHIVO_PDF_CONTENT_TYPE);
        return documento;
    }

    @BeforeEach
    public void initTest() {
        documento = createEntity(em);
    }

    @Test
    @Transactional
    public void createDocumento() throws Exception {
        int databaseSizeBeforeCreate = documentoRepository.findAll().size();

        // Create the Documento
        restDocumentoMockMvc.perform(post("/api/documentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documento)))
            .andExpect(status().isCreated());

        // Validate the Documento in the database
        List<Documento> documentoList = documentoRepository.findAll();
        assertThat(documentoList).hasSize(databaseSizeBeforeCreate + 1);
        Documento testDocumento = documentoList.get(documentoList.size() - 1);
        assertThat(testDocumento.getNombreDoc()).isEqualTo(DEFAULT_NOMBRE_DOC);
        assertThat(testDocumento.getDescDoc()).isEqualTo(DEFAULT_DESC_DOC);
        assertThat(testDocumento.getArchivo()).isEqualTo(DEFAULT_ARCHIVO);
        assertThat(testDocumento.getArchivoContentType()).isEqualTo(DEFAULT_ARCHIVO_CONTENT_TYPE);
        assertThat(testDocumento.getArchivoPdf()).isEqualTo(DEFAULT_ARCHIVO_PDF);
        assertThat(testDocumento.getArchivoPdfContentType()).isEqualTo(DEFAULT_ARCHIVO_PDF_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createDocumentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = documentoRepository.findAll().size();

        // Create the Documento with an existing ID
        documento.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentoMockMvc.perform(post("/api/documentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documento)))
            .andExpect(status().isBadRequest());

        // Validate the Documento in the database
        List<Documento> documentoList = documentoRepository.findAll();
        assertThat(documentoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDocumentos() throws Exception {
        // Initialize the database
        documentoRepository.saveAndFlush(documento);

        // Get all the documentoList
        restDocumentoMockMvc.perform(get("/api/documentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documento.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreDoc").value(hasItem(DEFAULT_NOMBRE_DOC.toString())))
            .andExpect(jsonPath("$.[*].descDoc").value(hasItem(DEFAULT_DESC_DOC.toString())))
            .andExpect(jsonPath("$.[*].archivoContentType").value(hasItem(DEFAULT_ARCHIVO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].archivo").value(hasItem(Base64Utils.encodeToString(DEFAULT_ARCHIVO))))
            .andExpect(jsonPath("$.[*].archivoPdfContentType").value(hasItem(DEFAULT_ARCHIVO_PDF_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].archivoPdf").value(hasItem(Base64Utils.encodeToString(DEFAULT_ARCHIVO_PDF))));
    }
    
    @Test
    @Transactional
    public void getDocumento() throws Exception {
        // Initialize the database
        documentoRepository.saveAndFlush(documento);

        // Get the documento
        restDocumentoMockMvc.perform(get("/api/documentos/{id}", documento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(documento.getId().intValue()))
            .andExpect(jsonPath("$.nombreDoc").value(DEFAULT_NOMBRE_DOC.toString()))
            .andExpect(jsonPath("$.descDoc").value(DEFAULT_DESC_DOC.toString()))
            .andExpect(jsonPath("$.archivoContentType").value(DEFAULT_ARCHIVO_CONTENT_TYPE))
            .andExpect(jsonPath("$.archivo").value(Base64Utils.encodeToString(DEFAULT_ARCHIVO)))
            .andExpect(jsonPath("$.archivoPdfContentType").value(DEFAULT_ARCHIVO_PDF_CONTENT_TYPE))
            .andExpect(jsonPath("$.archivoPdf").value(Base64Utils.encodeToString(DEFAULT_ARCHIVO_PDF)));
    }

    @Test
    @Transactional
    public void getNonExistingDocumento() throws Exception {
        // Get the documento
        restDocumentoMockMvc.perform(get("/api/documentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDocumento() throws Exception {
        // Initialize the database
        documentoRepository.saveAndFlush(documento);

        int databaseSizeBeforeUpdate = documentoRepository.findAll().size();

        // Update the documento
        Documento updatedDocumento = documentoRepository.findById(documento.getId()).get();
        // Disconnect from session so that the updates on updatedDocumento are not directly saved in db
        em.detach(updatedDocumento);
        updatedDocumento
            .nombreDoc(UPDATED_NOMBRE_DOC)
            .descDoc(UPDATED_DESC_DOC)
            .archivo(UPDATED_ARCHIVO)
            .archivoContentType(UPDATED_ARCHIVO_CONTENT_TYPE)
            .archivoPdf(UPDATED_ARCHIVO_PDF)
            .archivoPdfContentType(UPDATED_ARCHIVO_PDF_CONTENT_TYPE);

        restDocumentoMockMvc.perform(put("/api/documentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDocumento)))
            .andExpect(status().isOk());

        // Validate the Documento in the database
        List<Documento> documentoList = documentoRepository.findAll();
        assertThat(documentoList).hasSize(databaseSizeBeforeUpdate);
        Documento testDocumento = documentoList.get(documentoList.size() - 1);
        assertThat(testDocumento.getNombreDoc()).isEqualTo(UPDATED_NOMBRE_DOC);
        assertThat(testDocumento.getDescDoc()).isEqualTo(UPDATED_DESC_DOC);
        assertThat(testDocumento.getArchivo()).isEqualTo(UPDATED_ARCHIVO);
        assertThat(testDocumento.getArchivoContentType()).isEqualTo(UPDATED_ARCHIVO_CONTENT_TYPE);
        assertThat(testDocumento.getArchivoPdf()).isEqualTo(UPDATED_ARCHIVO_PDF);
        assertThat(testDocumento.getArchivoPdfContentType()).isEqualTo(UPDATED_ARCHIVO_PDF_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingDocumento() throws Exception {
        int databaseSizeBeforeUpdate = documentoRepository.findAll().size();

        // Create the Documento

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentoMockMvc.perform(put("/api/documentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documento)))
            .andExpect(status().isBadRequest());

        // Validate the Documento in the database
        List<Documento> documentoList = documentoRepository.findAll();
        assertThat(documentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDocumento() throws Exception {
        // Initialize the database
        documentoRepository.saveAndFlush(documento);

        int databaseSizeBeforeDelete = documentoRepository.findAll().size();

        // Delete the documento
        restDocumentoMockMvc.perform(delete("/api/documentos/{id}", documento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Documento> documentoList = documentoRepository.findAll();
        assertThat(documentoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Documento.class);
        Documento documento1 = new Documento();
        documento1.setId(1L);
        Documento documento2 = new Documento();
        documento2.setId(documento1.getId());
        assertThat(documento1).isEqualTo(documento2);
        documento2.setId(2L);
        assertThat(documento1).isNotEqualTo(documento2);
        documento1.setId(null);
        assertThat(documento1).isNotEqualTo(documento2);
    }
}
