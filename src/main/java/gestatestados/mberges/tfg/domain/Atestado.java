package gestatestados.mberges.tfg.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import gestatestados.mberges.tfg.domain.enumeration.EnumTipoJuicio;

/**
 * A Atestado.
 */
@Entity
@Table(name = "atestado")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Atestado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero")
    private String numero;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipojuicio")
    private EnumTipoJuicio tipojuicio;

    @Column(name = "fecha_atestado")
    private LocalDate fechaAtestado;

    @Column(name = "fecha_hora_suceso")
    private ZonedDateTime fechaHoraSuceso;

    @Column(name = "lugar")
    private String lugar;

    @Column(name = "accidente")
    private Boolean accidente;

    @Column(name = "permiso")
    private Boolean permiso;

    @Column(name = "alcoholemia")
    private Boolean alcoholemia;

    @Column(name = "bienes")
    private Boolean bienes;

    @Column(name = "velocidad")
    private Boolean velocidad;

    @Column(name = "lesiones")
    private Boolean lesiones;

    @Column(name = "fallecido")
    private Boolean fallecido;

    @Column(name = "instructor")
    private String instructor;

    @Column(name = "secretario")
    private String secretario;

    @OneToOne
    @JoinColumn(unique = true)
    private Destinatario destinatario;

    @OneToOne
    @JoinColumn(unique = true)
    private Remitente remitente;

    @OneToMany(mappedBy = "atestado", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Documento> documentos = new HashSet<>();

    @OneToMany(mappedBy = "atestado", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Implicado> implicados = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public Atestado numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public EnumTipoJuicio getTipojuicio() {
        return tipojuicio;
    }

    public Atestado tipojuicio(EnumTipoJuicio tipojuicio) {
        this.tipojuicio = tipojuicio;
        return this;
    }

    public void setTipojuicio(EnumTipoJuicio tipojuicio) {
        this.tipojuicio = tipojuicio;
    }

    public LocalDate getFechaAtestado() {
        return fechaAtestado;
    }

    public Atestado fechaAtestado(LocalDate fechaAtestado) {
        this.fechaAtestado = fechaAtestado;
        return this;
    }

    public void setFechaAtestado(LocalDate fechaAtestado) {
        this.fechaAtestado = fechaAtestado;
    }

    public ZonedDateTime getFechaHoraSuceso() {
        return fechaHoraSuceso;
    }

    public Atestado fechaHoraSuceso(ZonedDateTime fechaHoraSuceso) {
        this.fechaHoraSuceso = fechaHoraSuceso;
        return this;
    }

    public void setFechaHoraSuceso(ZonedDateTime fechaHoraSuceso) {
        this.fechaHoraSuceso = fechaHoraSuceso;
    }

    public String getLugar() {
        return lugar;
    }

    public Atestado lugar(String lugar) {
        this.lugar = lugar;
        return this;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public Boolean isAccidente() {
        return accidente;
    }

    public Atestado accidente(Boolean accidente) {
        this.accidente = accidente;
        return this;
    }

    public void setAccidente(Boolean accidente) {
        this.accidente = accidente;
    }

    public Boolean isPermiso() {
        return permiso;
    }

    public Atestado permiso(Boolean permiso) {
        this.permiso = permiso;
        return this;
    }

    public void setPermiso(Boolean permiso) {
        this.permiso = permiso;
    }

    public Boolean isAlcoholemia() {
        return alcoholemia;
    }

    public Atestado alcoholemia(Boolean alcoholemia) {
        this.alcoholemia = alcoholemia;
        return this;
    }

    public void setAlcoholemia(Boolean alcoholemia) {
        this.alcoholemia = alcoholemia;
    }

    public Boolean isBienes() {
        return bienes;
    }

    public Atestado bienes(Boolean bienes) {
        this.bienes = bienes;
        return this;
    }

    public void setBienes(Boolean bienes) {
        this.bienes = bienes;
    }

    public Boolean isVelocidad() {
        return velocidad;
    }

    public Atestado velocidad(Boolean velocidad) {
        this.velocidad = velocidad;
        return this;
    }

    public void setVelocidad(Boolean velocidad) {
        this.velocidad = velocidad;
    }

    public Boolean isLesiones() {
        return lesiones;
    }

    public Atestado lesiones(Boolean lesiones) {
        this.lesiones = lesiones;
        return this;
    }

    public void setLesiones(Boolean lesiones) {
        this.lesiones = lesiones;
    }

    public Boolean isFallecido() {
        return fallecido;
    }

    public Atestado fallecido(Boolean fallecido) {
        this.fallecido = fallecido;
        return this;
    }

    public void setFallecido(Boolean fallecido) {
        this.fallecido = fallecido;
    }

    public String getInstructor() {
        return instructor;
    }

    public Atestado instructor(String instructor) {
        this.instructor = instructor;
        return this;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getSecretario() {
        return secretario;
    }

    public Atestado secretario(String secretario) {
        this.secretario = secretario;
        return this;
    }

    public void setSecretario(String secretario) {
        this.secretario = secretario;
    }

    public Destinatario getDestinatario() {
        return destinatario;
    }

    public Atestado destinatario(Destinatario destinatario) {
        this.destinatario = destinatario;
        return this;
    }

    public void setDestinatario(Destinatario destinatario) {
        this.destinatario = destinatario;
    }

    public Remitente getRemitente() {
        return remitente;
    }

    public Atestado remitente(Remitente remitente) {
        this.remitente = remitente;
        return this;
    }

    public void setRemitente(Remitente remitente) {
        this.remitente = remitente;
    }

    public Set<Documento> getDocumentos() {
        return documentos;
    }

    public Atestado documentos(Set<Documento> documentos) {
        this.documentos = documentos;
        return this;
    }

    public Atestado addDocumento(Documento documento) {
        this.documentos.add(documento);
        documento.setAtestado(this);
        return this;
    }

    public Atestado removeDocumento(Documento documento) {
        this.documentos.remove(documento);
        documento.setAtestado(null);
        return this;
    }

    public void setDocumentos(Set<Documento> documentos) {
        this.documentos = documentos;
    }

    public Set<Implicado> getImplicados() {
        return implicados;
    }

    public Atestado implicados(Set<Implicado> implicados) {
        this.implicados = implicados;
        return this;
    }

    public Atestado addImplicado(Implicado implicado) {
        this.implicados.add(implicado);
        implicado.setAtestado(this);
        return this;
    }

    public Atestado removeImplicado(Implicado implicado) {
        this.implicados.remove(implicado);
        implicado.setAtestado(null);
        return this;
    }

    public void setImplicados(Set<Implicado> implicados) {
        this.implicados = implicados;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Atestado)) {
            return false;
        }
        return id != null && id.equals(((Atestado) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Atestado{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            ", tipojuicio='" + getTipojuicio() + "'" +
            ", fechaAtestado='" + getFechaAtestado() + "'" +
            ", fechaHoraSuceso='" + getFechaHoraSuceso() + "'" +
            ", lugar='" + getLugar() + "'" +
            ", accidente='" + isAccidente() + "'" +
            ", permiso='" + isPermiso() + "'" +
            ", alcoholemia='" + isAlcoholemia() + "'" +
            ", bienes='" + isBienes() + "'" +
            ", velocidad='" + isVelocidad() + "'" +
            ", lesiones='" + isLesiones() + "'" +
            ", fallecido='" + isFallecido() + "'" +
            ", instructor='" + getInstructor() + "'" +
            ", secretario='" + getSecretario() + "'" +
            "}";
    }
}
