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
 * Criteria class for the {@link com.santi.diccionario.domain.Clasificaciones} entity. This class is used
 * in {@link com.santi.diccionario.web.rest.ClasificacionesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /clasificaciones?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class ClasificacionesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter descripcion;

    private StringFilter cliente;

    private StringFilter remitente;

    private StringFilter destinatario;

    private StringFilter proveedor;

    private StringFilter codigoArancelarioOrigen;

    private DoubleFilter importe;

    private IntegerFilter uds;

    private DoubleFilter peso;

    private StringFilter codigoArancelarioObtenido;

    private LongFilter paisOrigenId;

    private LongFilter paisDestinoId;

    private LongFilter divisaId;

    private LongFilter idiomaId;

    private LongFilter refClienteId;

    private LongFilter provinciaDestinoId;

    private LongFilter idRemitenteId;

    private LongFilter idDestinatarioId;

    private Boolean distinct;

    public ClasificacionesCriteria() {}

    public ClasificacionesCriteria(ClasificacionesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.descripcion = other.descripcion == null ? null : other.descripcion.copy();
        this.cliente = other.cliente == null ? null : other.cliente.copy();
        this.remitente = other.remitente == null ? null : other.remitente.copy();
        this.destinatario = other.destinatario == null ? null : other.destinatario.copy();
        this.proveedor = other.proveedor == null ? null : other.proveedor.copy();
        this.codigoArancelarioOrigen = other.codigoArancelarioOrigen == null ? null : other.codigoArancelarioOrigen.copy();
        this.importe = other.importe == null ? null : other.importe.copy();
        this.uds = other.uds == null ? null : other.uds.copy();
        this.peso = other.peso == null ? null : other.peso.copy();
        this.codigoArancelarioObtenido = other.codigoArancelarioObtenido == null ? null : other.codigoArancelarioObtenido.copy();
        this.paisOrigenId = other.paisOrigenId == null ? null : other.paisOrigenId.copy();
        this.paisDestinoId = other.paisDestinoId == null ? null : other.paisDestinoId.copy();
        this.divisaId = other.divisaId == null ? null : other.divisaId.copy();
        this.idiomaId = other.idiomaId == null ? null : other.idiomaId.copy();
        this.refClienteId = other.refClienteId == null ? null : other.refClienteId.copy();
        this.provinciaDestinoId = other.provinciaDestinoId == null ? null : other.provinciaDestinoId.copy();
        this.idRemitenteId = other.idRemitenteId == null ? null : other.idRemitenteId.copy();
        this.idDestinatarioId = other.idDestinatarioId == null ? null : other.idDestinatarioId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ClasificacionesCriteria copy() {
        return new ClasificacionesCriteria(this);
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

    public StringFilter getCliente() {
        return cliente;
    }

    public StringFilter cliente() {
        if (cliente == null) {
            cliente = new StringFilter();
        }
        return cliente;
    }

    public void setCliente(StringFilter cliente) {
        this.cliente = cliente;
    }

    public StringFilter getRemitente() {
        return remitente;
    }

    public StringFilter remitente() {
        if (remitente == null) {
            remitente = new StringFilter();
        }
        return remitente;
    }

    public void setRemitente(StringFilter remitente) {
        this.remitente = remitente;
    }

    public StringFilter getDestinatario() {
        return destinatario;
    }

    public StringFilter destinatario() {
        if (destinatario == null) {
            destinatario = new StringFilter();
        }
        return destinatario;
    }

    public void setDestinatario(StringFilter destinatario) {
        this.destinatario = destinatario;
    }

    public StringFilter getProveedor() {
        return proveedor;
    }

    public StringFilter proveedor() {
        if (proveedor == null) {
            proveedor = new StringFilter();
        }
        return proveedor;
    }

    public void setProveedor(StringFilter proveedor) {
        this.proveedor = proveedor;
    }

    public StringFilter getCodigoArancelarioOrigen() {
        return codigoArancelarioOrigen;
    }

    public StringFilter codigoArancelarioOrigen() {
        if (codigoArancelarioOrigen == null) {
            codigoArancelarioOrigen = new StringFilter();
        }
        return codigoArancelarioOrigen;
    }

    public void setCodigoArancelarioOrigen(StringFilter codigoArancelarioOrigen) {
        this.codigoArancelarioOrigen = codigoArancelarioOrigen;
    }

    public DoubleFilter getImporte() {
        return importe;
    }

    public DoubleFilter importe() {
        if (importe == null) {
            importe = new DoubleFilter();
        }
        return importe;
    }

    public void setImporte(DoubleFilter importe) {
        this.importe = importe;
    }

    public IntegerFilter getUds() {
        return uds;
    }

    public IntegerFilter uds() {
        if (uds == null) {
            uds = new IntegerFilter();
        }
        return uds;
    }

    public void setUds(IntegerFilter uds) {
        this.uds = uds;
    }

    public DoubleFilter getPeso() {
        return peso;
    }

    public DoubleFilter peso() {
        if (peso == null) {
            peso = new DoubleFilter();
        }
        return peso;
    }

    public void setPeso(DoubleFilter peso) {
        this.peso = peso;
    }

    public StringFilter getCodigoArancelarioObtenido() {
        return codigoArancelarioObtenido;
    }

    public StringFilter codigoArancelarioObtenido() {
        if (codigoArancelarioObtenido == null) {
            codigoArancelarioObtenido = new StringFilter();
        }
        return codigoArancelarioObtenido;
    }

    public void setCodigoArancelarioObtenido(StringFilter codigoArancelarioObtenido) {
        this.codigoArancelarioObtenido = codigoArancelarioObtenido;
    }

    public LongFilter getPaisOrigenId() {
        return paisOrigenId;
    }

    public LongFilter paisOrigenId() {
        if (paisOrigenId == null) {
            paisOrigenId = new LongFilter();
        }
        return paisOrigenId;
    }

    public void setPaisOrigenId(LongFilter paisOrigenId) {
        this.paisOrigenId = paisOrigenId;
    }

    public LongFilter getPaisDestinoId() {
        return paisDestinoId;
    }

    public LongFilter paisDestinoId() {
        if (paisDestinoId == null) {
            paisDestinoId = new LongFilter();
        }
        return paisDestinoId;
    }

    public void setPaisDestinoId(LongFilter paisDestinoId) {
        this.paisDestinoId = paisDestinoId;
    }

    public LongFilter getDivisaId() {
        return divisaId;
    }

    public LongFilter divisaId() {
        if (divisaId == null) {
            divisaId = new LongFilter();
        }
        return divisaId;
    }

    public void setDivisaId(LongFilter divisaId) {
        this.divisaId = divisaId;
    }

    public LongFilter getIdiomaId() {
        return idiomaId;
    }

    public LongFilter idiomaId() {
        if (idiomaId == null) {
            idiomaId = new LongFilter();
        }
        return idiomaId;
    }

    public void setIdiomaId(LongFilter idiomaId) {
        this.idiomaId = idiomaId;
    }

    public LongFilter getRefClienteId() {
        return refClienteId;
    }

    public LongFilter refClienteId() {
        if (refClienteId == null) {
            refClienteId = new LongFilter();
        }
        return refClienteId;
    }

    public void setRefClienteId(LongFilter refClienteId) {
        this.refClienteId = refClienteId;
    }

    public LongFilter getProvinciaDestinoId() {
        return provinciaDestinoId;
    }

    public LongFilter provinciaDestinoId() {
        if (provinciaDestinoId == null) {
            provinciaDestinoId = new LongFilter();
        }
        return provinciaDestinoId;
    }

    public void setProvinciaDestinoId(LongFilter provinciaDestinoId) {
        this.provinciaDestinoId = provinciaDestinoId;
    }

    public LongFilter getIdRemitenteId() {
        return idRemitenteId;
    }

    public LongFilter idRemitenteId() {
        if (idRemitenteId == null) {
            idRemitenteId = new LongFilter();
        }
        return idRemitenteId;
    }

    public void setIdRemitenteId(LongFilter idRemitenteId) {
        this.idRemitenteId = idRemitenteId;
    }

    public LongFilter getIdDestinatarioId() {
        return idDestinatarioId;
    }

    public LongFilter idDestinatarioId() {
        if (idDestinatarioId == null) {
            idDestinatarioId = new LongFilter();
        }
        return idDestinatarioId;
    }

    public void setIdDestinatarioId(LongFilter idDestinatarioId) {
        this.idDestinatarioId = idDestinatarioId;
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
        final ClasificacionesCriteria that = (ClasificacionesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(descripcion, that.descripcion) &&
            Objects.equals(cliente, that.cliente) &&
            Objects.equals(remitente, that.remitente) &&
            Objects.equals(destinatario, that.destinatario) &&
            Objects.equals(proveedor, that.proveedor) &&
            Objects.equals(codigoArancelarioOrigen, that.codigoArancelarioOrigen) &&
            Objects.equals(importe, that.importe) &&
            Objects.equals(uds, that.uds) &&
            Objects.equals(peso, that.peso) &&
            Objects.equals(codigoArancelarioObtenido, that.codigoArancelarioObtenido) &&
            Objects.equals(paisOrigenId, that.paisOrigenId) &&
            Objects.equals(paisDestinoId, that.paisDestinoId) &&
            Objects.equals(divisaId, that.divisaId) &&
            Objects.equals(idiomaId, that.idiomaId) &&
            Objects.equals(refClienteId, that.refClienteId) &&
            Objects.equals(provinciaDestinoId, that.provinciaDestinoId) &&
            Objects.equals(idRemitenteId, that.idRemitenteId) &&
            Objects.equals(idDestinatarioId, that.idDestinatarioId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            descripcion,
            cliente,
            remitente,
            destinatario,
            proveedor,
            codigoArancelarioOrigen,
            importe,
            uds,
            peso,
            codigoArancelarioObtenido,
            paisOrigenId,
            paisDestinoId,
            divisaId,
            idiomaId,
            refClienteId,
            provinciaDestinoId,
            idRemitenteId,
            idDestinatarioId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClasificacionesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (descripcion != null ? "descripcion=" + descripcion + ", " : "") +
            (cliente != null ? "cliente=" + cliente + ", " : "") +
            (remitente != null ? "remitente=" + remitente + ", " : "") +
            (destinatario != null ? "destinatario=" + destinatario + ", " : "") +
            (proveedor != null ? "proveedor=" + proveedor + ", " : "") +
            (codigoArancelarioOrigen != null ? "codigoArancelarioOrigen=" + codigoArancelarioOrigen + ", " : "") +
            (importe != null ? "importe=" + importe + ", " : "") +
            (uds != null ? "uds=" + uds + ", " : "") +
            (peso != null ? "peso=" + peso + ", " : "") +
            (codigoArancelarioObtenido != null ? "codigoArancelarioObtenido=" + codigoArancelarioObtenido + ", " : "") +
            (paisOrigenId != null ? "paisOrigenId=" + paisOrigenId + ", " : "") +
            (paisDestinoId != null ? "paisDestinoId=" + paisDestinoId + ", " : "") +
            (divisaId != null ? "divisaId=" + divisaId + ", " : "") +
            (idiomaId != null ? "idiomaId=" + idiomaId + ", " : "") +
            (refClienteId != null ? "refClienteId=" + refClienteId + ", " : "") +
            (provinciaDestinoId != null ? "provinciaDestinoId=" + provinciaDestinoId + ", " : "") +
            (idRemitenteId != null ? "idRemitenteId=" + idRemitenteId + ", " : "") +
            (idDestinatarioId != null ? "idDestinatarioId=" + idDestinatarioId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
