package gestatestados.mberges.tfg.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

import gestatestados.mberges.tfg.domain.enumeration.EnumTipoRemitente;

/**
 * A Remitente.
 */
@Entity
@Table(name = "remitente")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Remitente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private EnumTipoRemitente tipo;

    @Column(name = "nombre_remitente")
    private String nombreRemitente;

    @Column(name = "desc_remitente")
    private String descRemitente;

    @OneToOne(mappedBy = "remitente")
    @JsonIgnore
    private Atestado atestado;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EnumTipoRemitente getTipo() {
        return tipo;
    }

    public Remitente tipo(EnumTipoRemitente tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(EnumTipoRemitente tipo) {
        this.tipo = tipo;
    }

    public String getNombreRemitente() {
        return nombreRemitente;
    }

    public Remitente nombreRemitente(String nombreRemitente) {
        this.nombreRemitente = nombreRemitente;
        return this;
    }

    public void setNombreRemitente(String nombreRemitente) {
        this.nombreRemitente = nombreRemitente;
    }

    public String getDescRemitente() {
        return descRemitente;
    }

    public Remitente descRemitente(String descRemitente) {
        this.descRemitente = descRemitente;
        return this;
    }

    public void setDescRemitente(String descRemitente) {
        this.descRemitente = descRemitente;
    }

    public Atestado getAtestado() {
        return atestado;
    }

    public Remitente atestado(Atestado atestado) {
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
        if (!(o instanceof Remitente)) {
            return false;
        }
        return id != null && id.equals(((Remitente) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Remitente{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            ", nombreRemitente='" + getNombreRemitente() + "'" +
            ", descRemitente='" + getDescRemitente() + "'" +
            "}";
    }
}
