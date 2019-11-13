package gestatestados.mberges.tfg.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Documento.
 */
@Entity
@Table(name = "documento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Documento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_doc")
    private String nombreDoc;

    @Column(name = "desc_doc")
    private String descDoc;

    @Lob
    @Column(name = "archivo")
    private byte[] archivo;

    @Column(name = "archivo_content_type")
    private String archivoContentType;

    @Lob
    @Column(name = "archivo_pdf")
    private byte[] archivoPdf;

    @Column(name = "archivo_pdf_content_type")
    private String archivoPdfContentType;

    @ManyToOne
    @JsonIgnoreProperties("documentos")
    private Atestado atestado;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreDoc() {
        return nombreDoc;
    }

    public Documento nombreDoc(String nombreDoc) {
        this.nombreDoc = nombreDoc;
        return this;
    }

    public void setNombreDoc(String nombreDoc) {
        this.nombreDoc = nombreDoc;
    }

    public String getDescDoc() {
        return descDoc;
    }

    public Documento descDoc(String descDoc) {
        this.descDoc = descDoc;
        return this;
    }

    public void setDescDoc(String descDoc) {
        this.descDoc = descDoc;
    }

    public byte[] getArchivo() {
        return archivo;
    }

    public Documento archivo(byte[] archivo) {
        this.archivo = archivo;
        return this;
    }

    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }

    public String getArchivoContentType() {
        return archivoContentType;
    }

    public Documento archivoContentType(String archivoContentType) {
        this.archivoContentType = archivoContentType;
        return this;
    }

    public void setArchivoContentType(String archivoContentType) {
        this.archivoContentType = archivoContentType;
    }

    public byte[] getArchivoPdf() {
        return archivoPdf;
    }

    public Documento archivoPdf(byte[] archivoPdf) {
        this.archivoPdf = archivoPdf;
        return this;
    }

    public void setArchivoPdf(byte[] archivoPdf) {
        this.archivoPdf = archivoPdf;
    }

    public String getArchivoPdfContentType() {
        return archivoPdfContentType;
    }

    public Documento archivoPdfContentType(String archivoPdfContentType) {
        this.archivoPdfContentType = archivoPdfContentType;
        return this;
    }

    public void setArchivoPdfContentType(String archivoPdfContentType) {
        this.archivoPdfContentType = archivoPdfContentType;
    }

    public Atestado getAtestado() {
        return atestado;
    }

    public Documento atestado(Atestado atestado) {
        this.atestado = atestado;
        return this;
    }

    public void setAtestado(Atestado atestado) {
        this.atestado = atestado;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Documento)) {
            return false;
        }
        return id != null && id.equals(((Documento) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Documento{" +
            "id=" + getId() +
            ", nombreDoc='" + getNombreDoc() + "'" +
            ", descDoc='" + getDescDoc() + "'" +
            ", archivo='" + getArchivo() + "'" +
            ", archivoContentType='" + getArchivoContentType() + "'" +
            ", archivoPdf='" + getArchivoPdf() + "'" +
            ", archivoPdfContentType='" + getArchivoPdfContentType() + "'" +
            "}";
    }
}
