package gestatestados.mberges.tfg.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A TasaAlcohol.
 */
@Entity
@Table(name = "tasa_alcohol")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TasaAlcohol implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tasa_no_evidencial")
    private Float tasaNoEvidencial;

    @Column(name = "tasa_evidencial_1")
    private Float tasaEvidencial1;

    @Column(name = "tasa_evidencial_2")
    private Float tasaEvidencial2;

    @Column(name = "tasa_en_sangre")
    private Float tasaEnSangre;

    @OneToOne(mappedBy = "tasaAlcohol")
    @JsonIgnore
    private Implicado implicado;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getTasaNoEvidencial() {
        return tasaNoEvidencial;
    }

    public TasaAlcohol tasaNoEvidencial(Float tasaNoEvidencial) {
        this.tasaNoEvidencial = tasaNoEvidencial;
        return this;
    }

    public void setTasaNoEvidencial(Float tasaNoEvidencial) {
        this.tasaNoEvidencial = tasaNoEvidencial;
    }

    public Float getTasaEvidencial1() {
        return tasaEvidencial1;
    }

    public TasaAlcohol tasaEvidencial1(Float tasaEvidencial1) {
        this.tasaEvidencial1 = tasaEvidencial1;
        return this;
    }

    public void setTasaEvidencial1(Float tasaEvidencial1) {
        this.tasaEvidencial1 = tasaEvidencial1;
    }

    public Float getTasaEvidencial2() {
        return tasaEvidencial2;
    }

    public TasaAlcohol tasaEvidencial2(Float tasaEvidencial2) {
        this.tasaEvidencial2 = tasaEvidencial2;
        return this;
    }

    public void setTasaEvidencial2(Float tasaEvidencial2) {
        this.tasaEvidencial2 = tasaEvidencial2;
    }

    public Float getTasaEnSangre() {
        return tasaEnSangre;
    }

    public TasaAlcohol tasaEnSangre(Float tasaEnSangre) {
        this.tasaEnSangre = tasaEnSangre;
        return this;
    }

    public void setTasaEnSangre(Float tasaEnSangre) {
        this.tasaEnSangre = tasaEnSangre;
    }

    public Implicado getImplicado() {
        return implicado;
    }

    public TasaAlcohol implicado(Implicado implicado) {
        this.implicado = implicado;
        return this;
    }

    public void setImplicado(Implicado implicado) {
        this.implicado = implicado;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TasaAlcohol)) {
            return false;
        }
        return id != null && id.equals(((TasaAlcohol) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TasaAlcohol{" +
            "id=" + getId() +
            ", tasaNoEvidencial=" + getTasaNoEvidencial() +
            ", tasaEvidencial1=" + getTasaEvidencial1() +
            ", tasaEvidencial2=" + getTasaEvidencial2() +
            ", tasaEnSangre=" + getTasaEnSangre() +
            "}";
    }
}
