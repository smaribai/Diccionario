package com.santi.diccionario.service;

import com.santi.diccionario.domain.*; // for static metamodels
import com.santi.diccionario.domain.Remitente;
import com.santi.diccionario.repository.RemitenteRepository;
import com.santi.diccionario.service.criteria.RemitenteCriteria;
import com.santi.diccionario.service.dto.RemitenteDTO;
import com.santi.diccionario.service.mapper.RemitenteMapper;
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
 * Service for executing complex queries for {@link Remitente} entities in the database.
 * The main input is a {@link RemitenteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RemitenteDTO} or a {@link Page} of {@link RemitenteDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RemitenteQueryService extends QueryService<Remitente> {

    private final Logger log = LoggerFactory.getLogger(RemitenteQueryService.class);

    private final RemitenteRepository remitenteRepository;

    private final RemitenteMapper remitenteMapper;

    public RemitenteQueryService(RemitenteRepository remitenteRepository, RemitenteMapper remitenteMapper) {
        this.remitenteRepository = remitenteRepository;
        this.remitenteMapper = remitenteMapper;
    }

    /**
     * Return a {@link List} of {@link RemitenteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RemitenteDTO> findByCriteria(RemitenteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Remitente> specification = createSpecification(criteria);
        return remitenteMapper.toDto(remitenteRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RemitenteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RemitenteDTO> findByCriteria(RemitenteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Remitente> specification = createSpecification(criteria);
        return remitenteRepository.findAll(specification, page).map(remitenteMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RemitenteCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Remitente> specification = createSpecification(criteria);
        return remitenteRepository.count(specification);
    }

    /**
     * Function to convert {@link RemitenteCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Remitente> createSpecification(RemitenteCriteria criteria) {
        Specification<Remitente> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Remitente_.id));
            }
            if (criteria.getIdRemitente() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIdRemitente(), Remitente_.idRemitente));
            }
            if (criteria.getNombre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombre(), Remitente_.nombre));
            }
            if (criteria.getDescripcion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescripcion(), Remitente_.descripcion));
            }
            if (criteria.getDireccion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDireccion(), Remitente_.direccion));
            }
        }
        return specification;
    }
}
