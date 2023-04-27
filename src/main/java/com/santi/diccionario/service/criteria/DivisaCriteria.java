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
 * Criteria class for the {@link com.santi.diccionario.domain.Divisa} entity. This class is used
 * in {@link com.santi.diccionario.web.rest.DivisaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /divisas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class DivisaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter codigoDivisa;

    private StringFilter nombreDivisa;

    private Boolean distinct;

    public DivisaCriteria() {}

    public DivisaCriteria(DivisaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.codigoDivisa = other.codigoDivisa == null ? null : other.codigoDivisa.copy();
        this.nombreDivisa = other.nombreDivisa == null ? null : other.nombreDivisa.copy();
        this.distinct = other.distinct;
    }

    @Override
    public DivisaCriteria copy() {
        return new DivisaCriteria(this);
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

    public StringFilter getCodigoDivisa() {
        return codigoDivisa;
    }

    public StringFilter codigoDivisa() {
        if (codigoDivisa == null) {
            codigoDivisa = new StringFilter();
        }
        return codigoDivisa;
    }

    public void setCodigoDivisa(StringFilter codigoDivisa) {
        this.codigoDivisa = codigoDivisa;
    }

    public StringFilter getNombreDivisa() {
        return nombreDivisa;
    }

    public StringFilter nombreDivisa() {
        if (nombreDivisa == null) {
            nombreDivisa = new StringFilter();
        }
        return nombreDivisa;
    }

    public void setNombreDivisa(StringFilter nombreDivisa) {
        this.nombreDivisa = nombreDivisa;
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
        final DivisaCriteria that = (DivisaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(codigoDivisa, that.codigoDivisa) &&
            Objects.equals(nombreDivisa, that.nombreDivisa) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codigoDivisa, nombreDivisa, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DivisaCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (codigoDivisa != null ? "codigoDivisa=" + codigoDivisa + ", " : "") +
            (nombreDivisa != null ? "nombreDivisa=" + nombreDivisa + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
