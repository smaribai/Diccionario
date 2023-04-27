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
 * Criteria class for the {@link com.santi.diccionario.domain.Producto} entity. This class is used
 * in {@link com.santi.diccionario.web.rest.ProductoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /productos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class ProductoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter codigoArancelario;

    private IntegerFilter nivel;

    private StringFilter cNCode;

    private IntegerFilter longitudCNCode;

    private StringFilter descripcion;

    private LongFilter parentId;

    private Boolean distinct;

    public ProductoCriteria() {}

    public ProductoCriteria(ProductoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.codigoArancelario = other.codigoArancelario == null ? null : other.codigoArancelario.copy();
        this.nivel = other.nivel == null ? null : other.nivel.copy();
        this.cNCode = other.cNCode == null ? null : other.cNCode.copy();
        this.longitudCNCode = other.longitudCNCode == null ? null : other.longitudCNCode.copy();
        this.descripcion = other.descripcion == null ? null : other.descripcion.copy();
        this.parentId = other.parentId == null ? null : other.parentId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ProductoCriteria copy() {
        return new ProductoCriteria(this);
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

    public StringFilter getCodigoArancelario() {
        return codigoArancelario;
    }

    public StringFilter codigoArancelario() {
        if (codigoArancelario == null) {
            codigoArancelario = new StringFilter();
        }
        return codigoArancelario;
    }

    public void setCodigoArancelario(StringFilter codigoArancelario) {
        this.codigoArancelario = codigoArancelario;
    }

    public IntegerFilter getNivel() {
        return nivel;
    }

    public IntegerFilter nivel() {
        if (nivel == null) {
            nivel = new IntegerFilter();
        }
        return nivel;
    }

    public void setNivel(IntegerFilter nivel) {
        this.nivel = nivel;
    }

    public StringFilter getcNCode() {
        return cNCode;
    }

    public StringFilter cNCode() {
        if (cNCode == null) {
            cNCode = new StringFilter();
        }
        return cNCode;
    }

    public void setcNCode(StringFilter cNCode) {
        this.cNCode = cNCode;
    }

    public IntegerFilter getLongitudCNCode() {
        return longitudCNCode;
    }

    public IntegerFilter longitudCNCode() {
        if (longitudCNCode == null) {
            longitudCNCode = new IntegerFilter();
        }
        return longitudCNCode;
    }

    public void setLongitudCNCode(IntegerFilter longitudCNCode) {
        this.longitudCNCode = longitudCNCode;
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

    public LongFilter getParentId() {
        return parentId;
    }

    public LongFilter parentId() {
        if (parentId == null) {
            parentId = new LongFilter();
        }
        return parentId;
    }

    public void setParentId(LongFilter parentId) {
        this.parentId = parentId;
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
        final ProductoCriteria that = (ProductoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(codigoArancelario, that.codigoArancelario) &&
            Objects.equals(nivel, that.nivel) &&
            Objects.equals(cNCode, that.cNCode) &&
            Objects.equals(longitudCNCode, that.longitudCNCode) &&
            Objects.equals(descripcion, that.descripcion) &&
            Objects.equals(parentId, that.parentId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codigoArancelario, nivel, cNCode, longitudCNCode, descripcion, parentId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (codigoArancelario != null ? "codigoArancelario=" + codigoArancelario + ", " : "") +
            (nivel != null ? "nivel=" + nivel + ", " : "") +
            (cNCode != null ? "cNCode=" + cNCode + ", " : "") +
            (longitudCNCode != null ? "longitudCNCode=" + longitudCNCode + ", " : "") +
            (descripcion != null ? "descripcion=" + descripcion + ", " : "") +
            (parentId != null ? "parentId=" + parentId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
