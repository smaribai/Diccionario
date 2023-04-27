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
 * Criteria class for the {@link com.santi.diccionario.domain.Traduccion} entity. This class is used
 * in {@link com.santi.diccionario.web.rest.TraduccionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /traduccions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class TraduccionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter textoOrigen;

    private StringFilter textoDestino;

    private LongFilter idiomaOrigenId;

    private LongFilter idiomaDestinoId;

    private Boolean distinct;

    public TraduccionCriteria() {}

    public TraduccionCriteria(TraduccionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.textoOrigen = other.textoOrigen == null ? null : other.textoOrigen.copy();
        this.textoDestino = other.textoDestino == null ? null : other.textoDestino.copy();
        this.idiomaOrigenId = other.idiomaOrigenId == null ? null : other.idiomaOrigenId.copy();
        this.idiomaDestinoId = other.idiomaDestinoId == null ? null : other.idiomaDestinoId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public TraduccionCriteria copy() {
        return new TraduccionCriteria(this);
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

    public StringFilter getTextoOrigen() {
        return textoOrigen;
    }

    public StringFilter textoOrigen() {
        if (textoOrigen == null) {
            textoOrigen = new StringFilter();
        }
        return textoOrigen;
    }

    public void setTextoOrigen(StringFilter textoOrigen) {
        this.textoOrigen = textoOrigen;
    }

    public StringFilter getTextoDestino() {
        return textoDestino;
    }

    public StringFilter textoDestino() {
        if (textoDestino == null) {
            textoDestino = new StringFilter();
        }
        return textoDestino;
    }

    public void setTextoDestino(StringFilter textoDestino) {
        this.textoDestino = textoDestino;
    }

    public LongFilter getIdiomaOrigenId() {
        return idiomaOrigenId;
    }

    public LongFilter idiomaOrigenId() {
        if (idiomaOrigenId == null) {
            idiomaOrigenId = new LongFilter();
        }
        return idiomaOrigenId;
    }

    public void setIdiomaOrigenId(LongFilter idiomaOrigenId) {
        this.idiomaOrigenId = idiomaOrigenId;
    }

    public LongFilter getIdiomaDestinoId() {
        return idiomaDestinoId;
    }

    public LongFilter idiomaDestinoId() {
        if (idiomaDestinoId == null) {
            idiomaDestinoId = new LongFilter();
        }
        return idiomaDestinoId;
    }

    public void setIdiomaDestinoId(LongFilter idiomaDestinoId) {
        this.idiomaDestinoId = idiomaDestinoId;
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
        final TraduccionCriteria that = (TraduccionCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(textoOrigen, that.textoOrigen) &&
            Objects.equals(textoDestino, that.textoDestino) &&
            Objects.equals(idiomaOrigenId, that.idiomaOrigenId) &&
            Objects.equals(idiomaDestinoId, that.idiomaDestinoId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, textoOrigen, textoDestino, idiomaOrigenId, idiomaDestinoId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TraduccionCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (textoOrigen != null ? "textoOrigen=" + textoOrigen + ", " : "") +
            (textoDestino != null ? "textoDestino=" + textoDestino + ", " : "") +
            (idiomaOrigenId != null ? "idiomaOrigenId=" + idiomaOrigenId + ", " : "") +
            (idiomaDestinoId != null ? "idiomaDestinoId=" + idiomaDestinoId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
