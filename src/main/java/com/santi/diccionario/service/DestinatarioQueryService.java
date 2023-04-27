package com.santi.diccionario.service;

import com.santi.diccionario.domain.*; // for static metamodels
import com.santi.diccionario.domain.Destinatario;
import com.santi.diccionario.repository.DestinatarioRepository;
import com.santi.diccionario.service.criteria.DestinatarioCriteria;
import com.santi.diccionario.service.dto.DestinatarioDTO;
import com.santi.diccionario.service.mapper.DestinatarioMapper;
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
 * Service for executing complex queries for {@link Destinatario} entities in the database.
 * The main input is a {@link DestinatarioCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DestinatarioDTO} or a {@link Page} of {@link DestinatarioDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DestinatarioQueryService extends QueryService<Destinatario> {

    private final Logger log = LoggerFactory.getLogger(DestinatarioQueryService.class);

    private final DestinatarioRepository destinatarioRepository;

    private final DestinatarioMapper destinatarioMapper;

    public DestinatarioQueryService(DestinatarioRepository destinatarioRepository, DestinatarioMapper destinatarioMapper) {
        this.destinatarioRepository = destinatarioRepository;
        this.destinatarioMapper = destinatarioMapper;
    }

    /**
     * Return a {@link List} of {@link DestinatarioDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DestinatarioDTO> findByCriteria(DestinatarioCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Destinatario> specification = createSpecification(criteria);
        return destinatarioMapper.toDto(destinatarioRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DestinatarioDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DestinatarioDTO> findByCriteria(DestinatarioCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Destinatario> specification = createSpecification(criteria);
        return destinatarioRepository.findAll(specification, page).map(destinatarioMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DestinatarioCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Destinatario> specification = createSpecification(criteria);
        return destinatarioRepository.count(specification);
    }

    /**
     * Function to convert {@link DestinatarioCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Destinatario> createSpecification(DestinatarioCriteria criteria) {
        Specification<Destinatario> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Destinatario_.id));
            }
            if (criteria.getIdDestinatario() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIdDestinatario(), Destinatario_.idDestinatario));
            }
            if (criteria.getNombre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombre(), Destinatario_.nombre));
            }
            if (criteria.getDescripcion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescripcion(), Destinatario_.descripcion));
            }
            if (criteria.getDireccion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDireccion(), Destinatario_.direccion));
            }
        }
        return specification;
    }
}
