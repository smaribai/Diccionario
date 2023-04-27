package com.santi.diccionario.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.santi.diccionario.domain.Clasificar} entity.
 */
public class ClasificarDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 250)
    private String descripcion;

    @Size(max = 100)
    private String cliente;

    @Size(max = 100)
    private String remitente;

    @Size(max = 200)
    private String destinatario;

    @Size(max = 200)
    private String proveedor;

    @Size(max = 20)
    private String codigoArancelarioOrigen;

    private Double importe;

    private Integer uds;

    private Double peso;

    private PaisDTO paisOrigen;

    private PaisDTO paisDestino;

    private DivisaDTO divisa;

    private IdiomaDTO idioma;

    private ClienteDTO refCliente;

    private ProvinciaDTO provinciaDestino;

    private RemitenteDTO idRemitente;

    private DestinatarioDTO idDestinatario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getRemitente() {
        return remitente;
    }

    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getCodigoArancelarioOrigen() {
        return codigoArancelarioOrigen;
    }

    public void setCodigoArancelarioOrigen(String codigoArancelarioOrigen) {
        this.codigoArancelarioOrigen = codigoArancelarioOrigen;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public Integer getUds() {
        return uds;
    }

    public void setUds(Integer uds) {
        this.uds = uds;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public PaisDTO getPaisOrigen() {
        return paisOrigen;
    }

    public void setPaisOrigen(PaisDTO paisOrigen) {
        this.paisOrigen = paisOrigen;
    }

    public PaisDTO getPaisDestino() {
        return paisDestino;
    }

    public void setPaisDestino(PaisDTO paisDestino) {
        this.paisDestino = paisDestino;
    }

    public DivisaDTO getDivisa() {
        return divisa;
    }

    public void setDivisa(DivisaDTO divisa) {
        this.divisa = divisa;
    }

    public IdiomaDTO getIdioma() {
        return idioma;
    }

    public void setIdioma(IdiomaDTO idioma) {
        this.idioma = idioma;
    }

    public ClienteDTO getRefCliente() {
        return refCliente;
    }

    public void setRefCliente(ClienteDTO refCliente) {
        this.refCliente = refCliente;
    }

    public ProvinciaDTO getProvinciaDestino() {
        return provinciaDestino;
    }

    public void setProvinciaDestino(ProvinciaDTO provinciaDestino) {
        this.provinciaDestino = provinciaDestino;
    }

    public RemitenteDTO getIdRemitente() {
        return idRemitente;
    }

    public void setIdRemitente(RemitenteDTO idRemitente) {
        this.idRemitente = idRemitente;
    }

    public DestinatarioDTO getIdDestinatario() {
        return idDestinatario;
    }

    public void setIdDestinatario(DestinatarioDTO idDestinatario) {
        this.idDestinatario = idDestinatario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClasificarDTO)) {
            return false;
        }

        ClasificarDTO clasificarDTO = (ClasificarDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, clasificarDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClasificarDTO{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            ", cliente='" + getCliente() + "'" +
            ", remitente='" + getRemitente() + "'" +
            ", destinatario='" + getDestinatario() + "'" +
            ", proveedor='" + getProveedor() + "'" +
            ", codigoArancelarioOrigen='" + getCodigoArancelarioOrigen() + "'" +
            ", importe=" + getImporte() +
            ", uds=" + getUds() +
            ", peso=" + getPeso() +
            ", paisOrigen=" + getPaisOrigen() +
            ", paisDestino=" + getPaisDestino() +
            ", divisa=" + getDivisa() +
            ", idioma=" + getIdioma() +
            ", refCliente=" + getRefCliente() +
            ", provinciaDestino=" + getProvinciaDestino() +
            ", idRemitente=" + getIdRemitente() +
            ", idDestinatario=" + getIdDestinatario() +
            "}";
    }
}
