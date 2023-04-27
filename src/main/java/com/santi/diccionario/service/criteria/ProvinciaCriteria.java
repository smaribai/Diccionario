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
 * Criteria class for the {@link com.santi.diccionario.domain.Provincia} entity. This class is used
 * in {@link com.santi.diccionario.web.rest.ProvinciaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /provincias?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class ProvinciaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter codigoProvincia;

    private StringFilter nombreProvincia;

    private Boolean distinct;

    public ProvinciaCriteria() {}

    public ProvinciaCriteria(ProvinciaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.codigoProvincia = other.codigoProvincia == null ? null : other.codigoProvincia.copy();
        this.nombreProvincia = other.nombreProvincia == null ? null : other.nombreProvincia.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ProvinciaCriteria copy() {
        return new ProvinciaCriteria(this);
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

    public StringFilter getCodigoProvincia() {
        return codigoProvincia;
    }

    public StringFilter codigoProvincia() {
        if (codigoProvincia == null) {
            codigoProvincia = new StringFilter();
        }
        return codigoProvincia;
    }

    public void setCodigoProvincia(StringFilter codigoProvincia) {
        this.codigoProvincia = codigoProvincia;
    }

    public StringFilter getNombreProvincia() {
        return nombreProvincia;
    }

    public StringFilter nombreProvincia() {
        if (nombreProvincia == null) {
            nombreProvincia = new StringFilter();
        }
        return nombreProvincia;
    }

    public void setNombreProvincia(StringFilter nombreProvincia) {
        this.nombreProvincia = nombreProvincia;
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
        final ProvinciaCriteria that = (ProvinciaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(codigoProvincia, that.codigoProvincia) &&
            Objects.equals(nombreProvincia, that.nombreProvincia) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codigoProvincia, nombreProvincia, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProvinciaCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (codigoProvincia != null ? "codigoProvincia=" + codigoProvincia + ", " : "") +
            (nombreProvincia != null ? "nombreProvincia=" + nombreProvincia + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
