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
 * Criteria class for the {@link com.santi.diccionario.domain.Destinatario} entity. This class is used
 * in {@link com.santi.diccionario.web.rest.DestinatarioResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /destinatarios?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class DestinatarioCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter idDestinatario;

    private StringFilter nombre;

    private StringFilter descripcion;

    private StringFilter direccion;

    private Boolean distinct;

    public DestinatarioCriteria() {}

    public DestinatarioCriteria(DestinatarioCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.idDestinatario = other.idDestinatario == null ? null : other.idDestinatario.copy();
        this.nombre = other.nombre == null ? null : other.nombre.copy();
        this.descripcion = other.descripcion == null ? null : other.descripcion.copy();
        this.direccion = other.direccion == null ? null : other.direccion.copy();
        this.distinct = other.distinct;
    }

    @Override
    public DestinatarioCriteria copy() {
        return new DestinatarioCriteria(this);
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

    public StringFilter getIdDestinatario() {
        return idDestinatario;
    }

    public StringFilter idDestinatario() {
        if (idDestinatario == null) {
            idDestinatario = new StringFilter();
        }
        return idDestinatario;
    }

    public void setIdDestinatario(StringFilter idDestinatario) {
        this.idDestinatario = idDestinatario;
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
        final DestinatarioCriteria that = (DestinatarioCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(idDestinatario, that.idDestinatario) &&
            Objects.equals(nombre, that.nombre) &&
            Objects.equals(descripcion, that.descripcion) &&
            Objects.equals(direccion, that.direccion) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idDestinatario, nombre, descripcion, direccion, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DestinatarioCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (idDestinatario != null ? "idDestinatario=" + idDestinatario + ", " : "") +
            (nombre != null ? "nombre=" + nombre + ", " : "") +
            (descripcion != null ? "descripcion=" + descripcion + ", " : "") +
            (direccion != null ? "direccion=" + direccion + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
