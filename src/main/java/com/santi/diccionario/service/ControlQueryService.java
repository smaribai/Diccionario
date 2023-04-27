package com.santi.diccionario.service;

import com.santi.diccionario.domain.*; // for static metamodels
import com.santi.diccionario.domain.Control;
import com.santi.diccionario.repository.ControlRepository;
import com.santi.diccionario.service.criteria.ControlCriteria;
import com.santi.diccionario.service.dto.ControlDTO;
import com.santi.diccionario.service.mapper.ControlMapper;
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
 * Service for executing complex queries for {@link Control} entities in the database.
 * The main input is a {@link ControlCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ControlDTO} or a {@link Page} of {@link ControlDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ControlQueryService extends QueryService<Control> {

    private final Logger log = LoggerFactory.getLogger(ControlQueryService.class);

    private final ControlRepository controlRepository;

    private final ControlMapper controlMapper;

    public ControlQueryService(ControlRepository controlRepository, ControlMapper controlMapper) {
        this.controlRepository = controlRepository;
        this.controlMapper = controlMapper;
    }

    /**
     * Return a {@link List} of {@link ControlDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ControlDTO> findByCriteria(ControlCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Control> specification = createSpecification(criteria);
        return controlMapper.toDto(controlRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ControlDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ControlDTO> findByCriteria(ControlCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Control> specification = createSpecification(criteria);
        return controlRepository.findAll(specification, page).map(controlMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ControlCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Control> specification = createSpecification(criteria);
        return controlRepository.count(specification);
    }

    /**
     * Function to convert {@link ControlCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Control> createSpecification(ControlCriteria criteria) {
        Specification<Control> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Control_.id));
            }
            if (criteria.getNombre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombre(), Control_.nombre));
            }
            if (criteria.getDescripcion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescripcion(), Control_.descripcion));
            }
        }
        return specification;
    }
}
