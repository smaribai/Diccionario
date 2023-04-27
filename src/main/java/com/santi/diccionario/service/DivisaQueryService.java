package com.santi.diccionario.service;

import com.santi.diccionario.domain.*; // for static metamodels
import com.santi.diccionario.domain.Divisa;
import com.santi.diccionario.repository.DivisaRepository;
import com.santi.diccionario.service.criteria.DivisaCriteria;
import com.santi.diccionario.service.dto.DivisaDTO;
import com.santi.diccionario.service.mapper.DivisaMapper;
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
 * Service for executing complex queries for {@link Divisa} entities in the database.
 * The main input is a {@link DivisaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DivisaDTO} or a {@link Page} of {@link DivisaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DivisaQueryService extends QueryService<Divisa> {

    private final Logger log = LoggerFactory.getLogger(DivisaQueryService.class);

    private final DivisaRepository divisaRepository;

    private final DivisaMapper divisaMapper;

    public DivisaQueryService(DivisaRepository divisaRepository, DivisaMapper divisaMapper) {
        this.divisaRepository = divisaRepository;
        this.divisaMapper = divisaMapper;
    }

    /**
     * Return a {@link List} of {@link DivisaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DivisaDTO> findByCriteria(DivisaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Divisa> specification = createSpecification(criteria);
        return divisaMapper.toDto(divisaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DivisaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DivisaDTO> findByCriteria(DivisaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Divisa> specification = createSpecification(criteria);
        return divisaRepository.findAll(specification, page).map(divisaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DivisaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Divisa> specification = createSpecification(criteria);
        return divisaRepository.count(specification);
    }

    /**
     * Function to convert {@link DivisaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Divisa> createSpecification(DivisaCriteria criteria) {
        Specification<Divisa> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Divisa_.id));
            }
            if (criteria.getCodigoDivisa() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodigoDivisa(), Divisa_.codigoDivisa));
            }
            if (criteria.getNombreDivisa() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombreDivisa(), Divisa_.nombreDivisa));
            }
        }
        return specification;
    }
}
