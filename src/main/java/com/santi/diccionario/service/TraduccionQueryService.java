package com.santi.diccionario.service;

import com.santi.diccionario.domain.*; // for static metamodels
import com.santi.diccionario.domain.Traduccion;
import com.santi.diccionario.repository.TraduccionRepository;
import com.santi.diccionario.service.criteria.TraduccionCriteria;
import com.santi.diccionario.service.dto.TraduccionDTO;
import com.santi.diccionario.service.mapper.TraduccionMapper;
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
 * Service for executing complex queries for {@link Traduccion} entities in the database.
 * The main input is a {@link TraduccionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TraduccionDTO} or a {@link Page} of {@link TraduccionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TraduccionQueryService extends QueryService<Traduccion> {

    private final Logger log = LoggerFactory.getLogger(TraduccionQueryService.class);

    private final TraduccionRepository traduccionRepository;

    private final TraduccionMapper traduccionMapper;

    public TraduccionQueryService(TraduccionRepository traduccionRepository, TraduccionMapper traduccionMapper) {
        this.traduccionRepository = traduccionRepository;
        this.traduccionMapper = traduccionMapper;
    }

    /**
     * Return a {@link List} of {@link TraduccionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TraduccionDTO> findByCriteria(TraduccionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Traduccion> specification = createSpecification(criteria);
        return traduccionMapper.toDto(traduccionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TraduccionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TraduccionDTO> findByCriteria(TraduccionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Traduccion> specification = createSpecification(criteria);
        return traduccionRepository.findAll(specification, page).map(traduccionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TraduccionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Traduccion> specification = createSpecification(criteria);
        return traduccionRepository.count(specification);
    }

    /**
     * Function to convert {@link TraduccionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Traduccion> createSpecification(TraduccionCriteria criteria) {
        Specification<Traduccion> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Traduccion_.id));
            }
            if (criteria.getTextoOrigen() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTextoOrigen(), Traduccion_.textoOrigen));
            }
            if (criteria.getTextoDestino() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTextoDestino(), Traduccion_.textoDestino));
            }
            if (criteria.getIdiomaOrigenId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getIdiomaOrigenId(),
                            root -> root.join(Traduccion_.idiomaOrigen, JoinType.LEFT).get(Idioma_.id)
                        )
                    );
            }
            if (criteria.getIdiomaDestinoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getIdiomaDestinoId(),
                            root -> root.join(Traduccion_.idiomaDestino, JoinType.LEFT).get(Idioma_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
