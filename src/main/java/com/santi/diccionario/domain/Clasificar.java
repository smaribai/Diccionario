package com.santi.diccionario.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Clasificar.
 */
@Entity
@Table(name = "clasificar")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Clasificar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 250)
    @Column(name = "descripcion", length = 250, nullable = false)
    private String descripcion;

    @Size(max = 100)
    @Column(name = "cliente", length = 100)
    private String cliente;

    @Size(max = 100)
    @Column(name = "remitente", length = 100)
    private String remitente;

    @Size(max = 200)
    @Column(name = "destinatario", length = 200)
    private String destinatario;

    @Size(max = 200)
    @Column(name = "proveedor", length = 200)
    private String proveedor;

    @Size(max = 20)
    @Column(name = "codigo_arancelario_origen", length = 20)
    private String codigoArancelarioOrigen;

    @Column(name = "importe")
    private Double importe;

    @Column(name = "uds")
    private Integer uds;

    @Column(name = "peso")
    private Double peso;

    @ManyToOne(optional = false)
    @NotNull
    private Pais paisOrigen;

    @ManyToOne(optional = false)
    @NotNull
    private Pais paisDestino;

    @ManyToOne
    private Divisa divisa;

    @ManyToOne
    private Idioma idioma;

    @ManyToOne
    @JsonIgnoreProperties(value = { "tipoCliente", "pais" }, allowSetters = true)
    private Cliente refCliente;

    @ManyToOne
    private Provincia provinciaDestino;

    @ManyToOne
    private Remitente idRemitente;

    @ManyToOne
    private Destinatario idDestinatario;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Clasificar id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Clasificar descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCliente() {
        return this.cliente;
    }

    public Clasificar cliente(String cliente) {
        this.setCliente(cliente);
        return this;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getRemitente() {
        return this.remitente;
    }

    public Clasificar remitente(String remitente) {
        this.setRemitente(remitente);
        return this;
    }

    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }

    public String getDestinatario() {
        return this.destinatario;
    }

    public Clasificar destinatario(String destinatario) {
        this.setDestinatario(destinatario);
        return this;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getProveedor() {
        return this.proveedor;
    }

    public Clasificar proveedor(String proveedor) {
        this.setProveedor(proveedor);
        return this;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getCodigoArancelarioOrigen() {
        return this.codigoArancelarioOrigen;
    }

    public Clasificar codigoArancelarioOrigen(String codigoArancelarioOrigen) {
        this.setCodigoArancelarioOrigen(codigoArancelarioOrigen);
        return this;
    }

    public void setCodigoArancelarioOrigen(String codigoArancelarioOrigen) {
        this.codigoArancelarioOrigen = codigoArancelarioOrigen;
    }

    public Double getImporte() {
        return this.importe;
    }

    public Clasificar importe(Double importe) {
        this.setImporte(importe);
        return this;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public Integer getUds() {
        return this.uds;
    }

    public Clasificar uds(Integer uds) {
        this.setUds(uds);
        return this;
    }

    public void setUds(Integer uds) {
        this.uds = uds;
    }

    public Double getPeso() {
        return this.peso;
    }

    public Clasificar peso(Double peso) {
        this.setPeso(peso);
        return this;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Pais getPaisOrigen() {
        return this.paisOrigen;
    }

    public void setPaisOrigen(Pais pais) {
        this.paisOrigen = pais;
    }

    public Clasificar paisOrigen(Pais pais) {
        this.setPaisOrigen(pais);
        return this;
    }

    public Pais getPaisDestino() {
        return this.paisDestino;
    }

    public void setPaisDestino(Pais pais) {
        this.paisDestino = pais;
    }

    public Clasificar paisDestino(Pais pais) {
        this.setPaisDestino(pais);
        return this;
    }

    public Divisa getDivisa() {
        return this.divisa;
    }

    public void setDivisa(Divisa divisa) {
        this.divisa = divisa;
    }

    public Clasificar divisa(Divisa divisa) {
        this.setDivisa(divisa);
        return this;
    }

    public Idioma getIdioma() {
        return this.idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public Clasificar idioma(Idioma idioma) {
        this.setIdioma(idioma);
        return this;
    }

    public Cliente getRefCliente() {
        return this.refCliente;
    }

    public void setRefCliente(Cliente cliente) {
        this.refCliente = cliente;
    }

    public Clasificar refCliente(Cliente cliente) {
        this.setRefCliente(cliente);
        return this;
    }

    public Provincia getProvinciaDestino() {
        return this.provinciaDestino;
    }

    public void setProvinciaDestino(Provincia provincia) {
        this.provinciaDestino = provincia;
    }

    public Clasificar provinciaDestino(Provincia provincia) {
        this.setProvinciaDestino(provincia);
        return this;
    }

    public Remitente getIdRemitente() {
        return this.idRemitente;
    }

    public void setIdRemitente(Remitente remitente) {
        this.idRemitente = remitente;
    }

    public Clasificar idRemitente(Remitente remitente) {
        this.setIdRemitente(remitente);
        return this;
    }

    public Destinatario getIdDestinatario() {
        return this.idDestinatario;
    }

    public void setIdDestinatario(Destinatario destinatario) {
        this.idDestinatario = destinatario;
    }

    public Clasificar idDestinatario(Destinatario destinatario) {
        this.setIdDestinatario(destinatario);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Clasificar)) {
            return false;
        }
        return id != null && id.equals(((Clasificar) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Clasificar{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            ", cliente='" + getCliente() + "'" +
            ", remitente='" + getRemitente() + "'" +
            ", destinatario='" + getDestinatario() + "'" +
            ", proveedor='" + getProveedor() + "'" +
            ", codigoArancelarioOrigen='" + getCodigoArancelarioOrigen() + "'" +
            ", importe=" + getImporte() +
            ", uds=" + getUds() +
            ", peso=" + getPeso() +
            "}";
    }
}
