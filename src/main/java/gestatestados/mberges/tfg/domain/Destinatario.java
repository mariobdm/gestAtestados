package gestatestados.mberges.tfg.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

import gestatestados.mberges.tfg.domain.enumeration.EnumTipoDestinatario;

/**
 * A Destinatario.
 */
@Entity
@Table(name = "destinatario")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Destinatario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private EnumTipoDestinatario tipo;

    @Column(name = "nombre_destinatario")
    private String nombreDestinatario;

    @Column(name = "desc_destinatario")
    private String descDestinatario;

    @OneToOne(mappedBy = "destinatario")
    @JsonIgnore
    private Atestado atestado;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EnumTipoDestinatario getTipo() {
        return tipo;
    }

    public Destinatario tipo(EnumTipoDestinatario tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(EnumTipoDestinatario tipo) {
        this.tipo = tipo;
    }

    public String getNombreDestinatario() {
        return nombreDestinatario;
    }

    public Destinatario nombreDestinatario(String nombreDestinatario) {
        this.nombreDestinatario = nombreDestinatario;
        return this;
    }

    public void setNombreDestinatario(String nombreDestinatario) {
        this.nombreDestinatario = nombreDestinatario;
    }

    public String getDescDestinatario() {
        return descDestinatario;
    }

    public Destinatario descDestinatario(String descDestinatario) {
        this.descDestinatario = descDestinatario;
        return this;
    }

    public void setDescDestinatario(String descDestinatario) {
        this.descDestinatario = descDestinatario;
    }

    public Atestado getAtestado() {
        return atestado;
    }

    public Destinatario atestado(Atestado atestado) {
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
        if (!(o instanceof Destinatario)) {
            return false;
        }
        return id != null && id.equals(((Destinatario) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Destinatario{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            ", nombreDestinatario='" + getNombreDestinatario() + "'" +
            ", descDestinatario='" + getDescDestinatario() + "'" +
            "}";
    }
}
