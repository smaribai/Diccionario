package com.santi.diccionario.service;

import com.santi.diccionario.domain.*; // for static metamodels
import com.santi.diccionario.domain.Clasificaciones;
import com.santi.diccionario.repository.ClasificacionesRepository;
import com.santi.diccionario.service.criteria.ClasificacionesCriteria;
import com.santi.diccionario.service.dto.ClasificacionesDTO;
import com.santi.diccionario.service.mapper.ClasificacionesMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Clasificaciones} entities in the database.
 * The main input is a {@link ClasificacionesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ClasificacionesDTO} or a {@link Page} of {@link ClasificacionesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ClasificacionesQueryService extends QueryService<Clasificaciones> {

    private final Logger log = LoggerFactory.getLogger(ClasificacionesQueryService.class);

    private final ClasificacionesRepository clasificacionesRepository;

    private final ClasificacionesMapper clasificacionesMapper;

    public ClasificacionesQueryService(ClasificacionesRepository clasificacionesRepository, ClasificacionesMapper clasificacionesMapper) {
        this.clasificacionesRepository = clasificacionesRepository;
        this.clasificacionesMapper = clasificacionesMapper;
    }

    /**
     * Return a {@link List} of {@link ClasificacionesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ClasificacionesDTO> findByCriteria(ClasificacionesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Clasificaciones> specification = createSpecification(criteria);
        return clasificacionesMapper.toDto(clasificacionesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ClasificacionesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ClasificacionesDTO> findByCriteria(ClasificacionesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Clasificaciones> specification = createSpecification(criteria);
        return clasificacionesRepository.findAll(specification, page).map(clasificacionesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ClasificacionesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Clasificaciones> specification = createSpecification(criteria);
        return clasificacionesRepository.count(specification);
    }

    /**
     * Function to convert {@link ClasificacionesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Clasificaciones> createSpecification(ClasificacionesCriteria criteria) {
        Specification<Clasificaciones> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Clasificaciones_.id));
            }
            if (criteria.getDescripcion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescripcion(), Clasificaciones_.descripcion));
            }
            if (criteria.getCliente() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCliente(), Clasificaciones_.cliente));
            }
            if (criteria.getRemitente() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemitente(), Clasificaciones_.remitente));
            }
            if (criteria.getDestinatario() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDestinatario(), Clasificaciones_.destinatario));
            }
            if (criteria.getProveedor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProveedor(), Clasificaciones_.proveedor));
            }
            if (criteria.getCodigoArancelarioOrigen() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getCodigoArancelarioOrigen(), Clasificaciones_.codigoArancelarioOrigen)
                    );
            }
            if (criteria.getImporte() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getImporte(), Clasificaciones_.importe));
            }
            if (criteria.getUds() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUds(), Clasificaciones_.uds));
            }
            if (criteria.getPeso() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPeso(), Clasificaciones_.peso));
            }
            if (criteria.getCodigoArancelarioObtenido() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getCodigoArancelarioObtenido(), Clasificaciones_.codigoArancelarioObtenido)
                    );
            }
            if (criteria.getPaisOrigenId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPaisOrigenId(),
                            root -> root.join(Clasificaciones_.paisOrigen, JoinType.LEFT).get(Pais_.id)
                        )
                    );
            }
            if (criteria.getPaisDestinoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPaisDestinoId(),
                            root -> root.join(Clasificaciones_.paisDestino, JoinType.LEFT).get(Pais_.id)
                        )
                    );
            }
            if (criteria.getDivisaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDivisaId(),
                            root -> root.join(Clasificaciones_.divisa, JoinType.LEFT).get(Divisa_.id)
                        )
                    );
            }
            if (criteria.getIdiomaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getIdiomaId(),
                            root -> root.join(Clasificaciones_.idioma, JoinType.LEFT).get(Idioma_.id)
                        )
                    );
            }
            if (criteria.getRefClienteId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getRefClienteId(),
                            root -> root.join(Clasificaciones_.refCliente, JoinType.LEFT).get(Cliente_.id)
                        )
                    );
            }
            if (criteria.getProvinciaDestinoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProvinciaDestinoId(),
                            root -> root.join(Clasificaciones_.provinciaDestino, JoinType.LEFT).get(Provincia_.id)
                        )
                    );
            }
            if (criteria.getIdRemitenteId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getIdRemitenteId(),
                            root -> root.join(Clasificaciones_.idRemitente, JoinType.LEFT).get(Remitente_.id)
                        )
                    );
            }
            if (criteria.getIdDestinatarioId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getIdDestinatarioId(),
                            root -> root.join(Clasificaciones_.idDestinatario, JoinType.LEFT).get(Destinatario_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
