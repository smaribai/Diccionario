package com.santi.diccionario.service;

import com.santi.diccionario.domain.*; // for static metamodels
import com.santi.diccionario.domain.Clasificar;
import com.santi.diccionario.repository.ClasificarRepository;
import com.santi.diccionario.service.criteria.ClasificarCriteria;
import com.santi.diccionario.service.dto.ClasificarDTO;
import com.santi.diccionario.service.mapper.ClasificarMapper;
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
 * Service for executing complex queries for {@link Clasificar} entities in the database.
 * The main input is a {@link ClasificarCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ClasificarDTO} or a {@link Page} of {@link ClasificarDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ClasificarQueryService extends QueryService<Clasificar> {

    private final Logger log = LoggerFactory.getLogger(ClasificarQueryService.class);

    private final ClasificarRepository clasificarRepository;

    private final ClasificarMapper clasificarMapper;

    public ClasificarQueryService(ClasificarRepository clasificarRepository, ClasificarMapper clasificarMapper) {
        this.clasificarRepository = clasificarRepository;
        this.clasificarMapper = clasificarMapper;
    }

    /**
     * Return a {@link List} of {@link ClasificarDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ClasificarDTO> findByCriteria(ClasificarCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Clasificar> specification = createSpecification(criteria);
        return clasificarMapper.toDto(clasificarRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ClasificarDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ClasificarDTO> findByCriteria(ClasificarCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Clasificar> specification = createSpecification(criteria);
        return clasificarRepository.findAll(specification, page).map(clasificarMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ClasificarCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Clasificar> specification = createSpecification(criteria);
        return clasificarRepository.count(specification);
    }

    /**
     * Function to convert {@link ClasificarCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Clasificar> createSpecification(ClasificarCriteria criteria) {
        Specification<Clasificar> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Clasificar_.id));
            }
            if (criteria.getDescripcion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescripcion(), Clasificar_.descripcion));
            }
            if (criteria.getCliente() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCliente(), Clasificar_.cliente));
            }
            if (criteria.getRemitente() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemitente(), Clasificar_.remitente));
            }
            if (criteria.getDestinatario() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDestinatario(), Clasificar_.destinatario));
            }
            if (criteria.getProveedor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProveedor(), Clasificar_.proveedor));
            }
            if (criteria.getCodigoArancelarioOrigen() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getCodigoArancelarioOrigen(), Clasificar_.codigoArancelarioOrigen));
            }
            if (criteria.getImporte() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getImporte(), Clasificar_.importe));
            }
            if (criteria.getUds() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUds(), Clasificar_.uds));
            }
            if (criteria.getPeso() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPeso(), Clasificar_.peso));
            }
            if (criteria.getPaisOrigenId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPaisOrigenId(),
                            root -> root.join(Clasificar_.paisOrigen, JoinType.LEFT).get(Pais_.id)
                        )
                    );
            }
            if (criteria.getPaisDestinoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPaisDestinoId(),
                            root -> root.join(Clasificar_.paisDestino, JoinType.LEFT).get(Pais_.id)
                        )
                    );
            }
            if (criteria.getDivisaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getDivisaId(), root -> root.join(Clasificar_.divisa, JoinType.LEFT).get(Divisa_.id))
                    );
            }
            if (criteria.getIdiomaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getIdiomaId(), root -> root.join(Clasificar_.idioma, JoinType.LEFT).get(Idioma_.id))
                    );
            }
            if (criteria.getRefClienteId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getRefClienteId(),
                            root -> root.join(Clasificar_.refCliente, JoinType.LEFT).get(Cliente_.id)
                        )
                    );
            }
            if (criteria.getProvinciaDestinoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProvinciaDestinoId(),
                            root -> root.join(Clasificar_.provinciaDestino, JoinType.LEFT).get(Provincia_.id)
                        )
                    );
            }
            if (criteria.getIdRemitenteId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getIdRemitenteId(),
                            root -> root.join(Clasificar_.idRemitente, JoinType.LEFT).get(Remitente_.id)
                        )
                    );
            }
            if (criteria.getIdDestinatarioId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getIdDestinatarioId(),
                            root -> root.join(Clasificar_.idDestinatario, JoinType.LEFT).get(Destinatario_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
