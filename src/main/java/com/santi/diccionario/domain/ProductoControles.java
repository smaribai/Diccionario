package com.santi.diccionario.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ProductoControles.
 */
@Entity
@Table(name = "controles_producto")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProductoControles implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 200)
    @Column(name = "descripcion", length = 200)
    private String descripcion;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "parent" }, allowSetters = true)
    private Producto codigoArancelario;

    @ManyToOne(optional = false)
    @NotNull
    private Control idControl;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ProductoControles id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public ProductoControles descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Producto getCodigoArancelario() {
        return this.codigoArancelario;
    }

    public void setCodigoArancelario(Producto producto) {
        this.codigoArancelario = producto;
    }

    public ProductoControles codigoArancelario(Producto producto) {
        this.setCodigoArancelario(producto);
        return this;
    }

    public Control getIdControl() {
        return this.idControl;
    }

    public void setIdControl(Control control) {
        this.idControl = control;
    }

    public ProductoControles idControl(Control control) {
        this.setIdControl(control);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductoControles)) {
            return false;
        }
        return id != null && id.equals(((ProductoControles) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductoControles{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
