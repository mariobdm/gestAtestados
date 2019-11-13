package gestatestados.mberges.tfg.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

import gestatestados.mberges.tfg.domain.enumeration.EnumTipoDocumentacion;

import gestatestados.mberges.tfg.domain.enumeration.EnumTipoImplicado;

/**
 * A Implicado.
 */
@Entity
@Table(name = "implicado")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Implicado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_documento")
    private EnumTipoDocumentacion tipoDocumento;

    @Column(name = "documento")
    private String documento;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido_1")
    private String apellido1;

    @Column(name = "apellido_2")
    private String apellido2;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "telefono")
    private String telefono;

    @Enumerated(EnumType.STRING)
    @Column(name = "calidad")
    private EnumTipoImplicado calidad;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "municipio")
    private String municipio;

    @Column(name = "codigopostal")
    private Integer codigopostal;

    @OneToOne
    @JoinColumn(unique = true)
    private TasaAlcohol tasaAlcohol;

    @ManyToOne
    @JsonIgnoreProperties("implicados")
    private Atestado atestado;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EnumTipoDocumentacion getTipoDocumento() {
        return tipoDocumento;
    }

    public Implicado tipoDocumento(EnumTipoDocumentacion tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
        return this;
    }

    public void setTipoDocumento(EnumTipoDocumentacion tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getDocumento() {
        return documento;
    }

    public Implicado documento(String documento) {
        this.documento = documento;
        return this;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public Implicado nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public Implicado apellido1(String apellido1) {
        this.apellido1 = apellido1;
        return this;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public Implicado apellido2(String apellido2) {
        this.apellido2 = apellido2;
        return this;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public Implicado fechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
        return this;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTelefono() {
        return telefono;
    }

    public Implicado telefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public EnumTipoImplicado getCalidad() {
        return calidad;
    }

    public Implicado calidad(EnumTipoImplicado calidad) {
        this.calidad = calidad;
        return this;
    }

    public void setCalidad(EnumTipoImplicado calidad) {
        this.calidad = calidad;
    }

    public String getDireccion() {
        return direccion;
    }

    public Implicado direccion(String direccion) {
        this.direccion = direccion;
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getMunicipio() {
        return municipio;
    }

    public Implicado municipio(String municipio) {
        this.municipio = municipio;
        return this;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public Integer getCodigopostal() {
        return codigopostal;
    }

    public Implicado codigopostal(Integer codigopostal) {
        this.codigopostal = codigopostal;
        return this;
    }

    public void setCodigopostal(Integer codigopostal) {
        this.codigopostal = codigopostal;
    }

    public TasaAlcohol getTasaAlcohol() {
        return tasaAlcohol;
    }

    public Implicado tasaAlcohol(TasaAlcohol tasaAlcohol) {
        this.tasaAlcohol = tasaAlcohol;
        return this;
    }

    public void setTasaAlcohol(TasaAlcohol tasaAlcohol) {
        this.tasaAlcohol = tasaAlcohol;
    }

    public Atestado getAtestado() {
        return atestado;
    }

    public Implicado atestado(Atestado atestado) {
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
        if (!(o instanceof Implicado)) {
            return false;
        }
        return id != null && id.equals(((Implicado) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Implicado{" +
            "id=" + getId() +
            ", tipoDocumento='" + getTipoDocumento() + "'" +
            ", documento='" + getDocumento() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", apellido1='" + getApellido1() + "'" +
            ", apellido2='" + getApellido2() + "'" +
            ", fechaNacimiento='" + getFechaNacimiento() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", calidad='" + getCalidad() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", municipio='" + getMunicipio() + "'" +
            ", codigopostal=" + getCodigopostal() +
            "}";
    }
}
