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
 * Criteria class for the {@link com.santi.diccionario.domain.ProductoControles} entity. This class is used
 * in {@link com.santi.diccionario.web.rest.ProductoControlesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /producto-controles?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class ProductoControlesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter descripcion;

    private LongFilter codigoArancelarioId;

    private LongFilter idControlId;

    private Boolean distinct;

    public ProductoControlesCriteria() {}

    public ProductoControlesCriteria(ProductoControlesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.descripcion = other.descripcion == null ? null : other.descripcion.copy();
        this.codigoArancelarioId = other.codigoArancelarioId == null ? null : other.codigoArancelarioId.copy();
        this.idControlId = other.idControlId == null ? null : other.idControlId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ProductoControlesCriteria copy() {
        return new ProductoControlesCriteria(this);
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

    public LongFilter getCodigoArancelarioId() {
        return codigoArancelarioId;
    }

    public LongFilter codigoArancelarioId() {
        if (codigoArancelarioId == null) {
            codigoArancelarioId = new LongFilter();
        }
        return codigoArancelarioId;
    }

    public void setCodigoArancelarioId(LongFilter codigoArancelarioId) {
        this.codigoArancelarioId = codigoArancelarioId;
    }

    public LongFilter getIdControlId() {
        return idControlId;
    }

    public LongFilter idControlId() {
        if (idControlId == null) {
            idControlId = new LongFilter();
        }
        return idControlId;
    }

    public void setIdControlId(LongFilter idControlId) {
        this.idControlId = idControlId;
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
        final ProductoControlesCriteria that = (ProductoControlesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(descripcion, that.descripcion) &&
            Objects.equals(codigoArancelarioId, that.codigoArancelarioId) &&
            Objects.equals(idControlId, that.idControlId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, descripcion, codigoArancelarioId, idControlId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductoControlesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (descripcion != null ? "descripcion=" + descripcion + ", " : "") +
            (codigoArancelarioId != null ? "codigoArancelarioId=" + codigoArancelarioId + ", " : "") +
            (idControlId != null ? "idControlId=" + idControlId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
