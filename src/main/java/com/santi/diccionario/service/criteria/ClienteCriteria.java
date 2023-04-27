package com.santi.diccionario.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.santi.diccionario.domain.Cliente} entity. This class is used
 * in {@link com.santi.diccionario.web.rest.ClienteResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /clientes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class ClienteCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter idCliente;

    private StringFilter nombre;

    private StringFilter descripcion;

    private StringFilter direccion;

    private LongFilter tipoClienteId;

    private LongFilter paisId;

    private Boolean distinct;

    public ClienteCriteria() {}

    public ClienteCriteria(ClienteCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.idCliente = other.idCliente == null ? null : other.idCliente.copy();
        this.nombre = other.nombre == null ? null : other.nombre.copy();
        this.descripcion = other.descripcion == null ? null : other.descripcion.copy();
        this.direccion = other.direccion == null ? null : other.direccion.copy();
        this.tipoClienteId = other.tipoClienteId == null ? null : other.tipoClienteId.copy();
        this.paisId = other.paisId == null ? null : other.paisId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ClienteCriteria copy() {
        return new ClienteCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getIdCliente() {
        return idCliente;
    }

    public StringFilter idCliente() {
        if (idCliente == null) {
            idCliente = new StringFilter();
        }
        return idCliente;
    }

    public void setIdCliente(StringFilter idCliente) {
        this.idCliente = idCliente;
    }

    public StringFilter getNombre() {
        return nombre;
    }

    public StringFilter nombre() {
        if (nombre == null) {
            nombre = new StringFilter();
        }
        return nombre;
    }

    public void setNombre(StringFilter nombre) {
        this.nombre = nombre;
    }

    public StringFilter getDescripcion() {
        return descripcion;
    }

    public StringFilter descripcion() {
        if (descripcion == null) {
            descripcion = new StringFilter();
        }
        return descripcion;
    }

    public void setDescripcion(StringFilter descripcion) {
        this.descripcion = descripcion;
    }

    public StringFilter getDireccion() {
        return direccion;
    }

    public StringFilter direccion() {
        if (direccion == null) {
            direccion = new StringFilter();
        }
        return direccion;
    }

    public void setDireccion(StringFilter direccion) {
        this.direccion = direccion;
    }

    public LongFilter getTipoClienteId() {
        return tipoClienteId;
    }

    public LongFilter tipoClienteId() {
        if (tipoClienteId == null) {
            tipoClienteId = new LongFilter();
        }
        return tipoClienteId;
    }

    public void setTipoClienteId(LongFilter tipoClienteId) {
        this.tipoClienteId = tipoClienteId;
    }

    public LongFilter getPaisId() {
        return paisId;
    }

    public LongFilter paisId() {
        if (paisId == null) {
            paisId = new LongFilter();
        }
        return paisId;
    }

    public void setPaisId(LongFilter paisId) {
        this.paisId = paisId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ClienteCriteria that = (ClienteCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(idCliente, that.idCliente) &&
            Objects.equals(nombre, that.nombre) &&
            Objects.equals(descripcion, that.descripcion) &&
            Objects.equals(direccion, that.direccion) &&
            Objects.equals(tipoClienteId, that.tipoClienteId) &&
            Objects.equals(paisId, that.paisId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idCliente, nombre, descripcion, direccion, tipoClienteId, paisId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClienteCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (idCliente != null ? "idCliente=" + idCliente + ", " : "") +
            (nombre != null ? "nombre=" + nombre + ", " : "") +
            (descripcion != null ? "descripcion=" + descripcion + ", " : "") +
            (direccion != null ? "direccion=" + direccion + ", " : "") +
            (tipoClienteId != null ? "tipoClienteId=" + tipoClienteId + ", " : "") +
            (paisId != null ? "paisId=" + paisId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
