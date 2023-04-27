package com.santi.diccionario.service;

import com.santi.diccionario.domain.*; // for static metamodels
import com.santi.diccionario.domain.TipoCliente;
import com.santi.diccionario.repository.TipoClienteRepository;
import com.santi.diccionario.service.criteria.TipoClienteCriteria;
import com.santi.diccionario.service.dto.TipoClienteDTO;
import com.santi.diccionario.service.mapper.TipoClienteMapper;
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
 * Service for executing complex queries for {@link TipoCliente} entities in the database.
 * The main input is a {@link TipoClienteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TipoClienteDTO} or a {@link Page} of {@link TipoClienteDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TipoClienteQueryService extends QueryService<TipoCliente> {

    private final Logger log = LoggerFactory.getLogger(TipoClienteQueryService.class);

    private final TipoClienteRepository tipoClienteRepository;

    private final TipoClienteMapper tipoClienteMapper;

    public TipoClienteQueryService(TipoClienteRepository tipoClienteRepository, TipoClienteMapper tipoClienteMapper) {
        this.tipoClienteRepository = tipoClienteRepository;
        this.tipoClienteMapper = tipoClienteMapper;
    }

    /**
     * Return a {@link List} of {@link TipoClienteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TipoClienteDTO> findByCriteria(TipoClienteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TipoCliente> specification = createSpecification(criteria);
        return tipoClienteMapper.toDto(tipoClienteRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TipoClienteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TipoClienteDTO> findByCriteria(TipoClienteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TipoCliente> specification = createSpecification(criteria);
        return tipoClienteRepository.findAll(specification, page).map(tipoClienteMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TipoClienteCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TipoCliente> specification = createSpecification(criteria);
        return tipoClienteRepository.count(specification);
    }

    /**
     * Function to convert {@link TipoClienteCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TipoCliente> createSpecification(TipoClienteCriteria criteria) {
        Specification<TipoCliente> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TipoCliente_.id));
            }
            if (criteria.getCodigo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodigo(), TipoCliente_.codigo));
            }
            if (criteria.getNombre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombre(), TipoCliente_.nombre));
            }
        }
        return specification;
    }
}
