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
 * Criteria class for the {@link com.santi.diccionario.domain.Pais} entity. This class is used
 * in {@link com.santi.diccionario.web.rest.PaisResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /pais?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class PaisCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter codigoPais;

    private StringFilter nombrePais;

    private Boolean distinct;

    public PaisCriteria() {}

    public PaisCriteria(PaisCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.codigoPais = other.codigoPais == null ? null : other.codigoPais.copy();
        this.nombrePais = other.nombrePais == null ? null : other.nombrePais.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PaisCriteria copy() {
        return new PaisCriteria(this);
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

    public StringFilter getCodigoPais() {
        return codigoPais;
    }

    public StringFilter codigoPais() {
        if (codigoPais == null) {
            codigoPais = new StringFilter();
        }
        return codigoPais;
    }

    public void setCodigoPais(StringFilter codigoPais) {
        this.codigoPais = codigoPais;
    }

    public StringFilter getNombrePais() {
        return nombrePais;
    }

    public StringFilter nombrePais() {
        if (nombrePais == null) {
            nombrePais = new StringFilter();
        }
        return nombrePais;
    }

    public void setNombrePais(StringFilter nombrePais) {
        this.nombrePais = nombrePais;
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
        final PaisCriteria that = (PaisCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(codigoPais, that.codigoPais) &&
            Objects.equals(nombrePais, that.nombrePais) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codigoPais, nombrePais, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaisCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (codigoPais != null ? "codigoPais=" + codigoPais + ", " : "") +
            (nombrePais != null ? "nombrePais=" + nombrePais + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
