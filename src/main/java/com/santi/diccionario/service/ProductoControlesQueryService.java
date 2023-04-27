package com.santi.diccionario.service;

import com.santi.diccionario.domain.*; // for static metamodels
import com.santi.diccionario.domain.ProductoControles;
import com.santi.diccionario.repository.ProductoControlesRepository;
import com.santi.diccionario.service.criteria.ProductoControlesCriteria;
import com.santi.diccionario.service.dto.ProductoControlesDTO;
import com.santi.diccionario.service.mapper.ProductoControlesMapper;
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
 * Service for executing complex queries for {@link ProductoControles} entities in the database.
 * The main input is a {@link ProductoControlesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductoControlesDTO} or a {@link Page} of {@link ProductoControlesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductoControlesQueryService extends QueryService<ProductoControles> {

    private final Logger log = LoggerFactory.getLogger(ProductoControlesQueryService.class);

    private final ProductoControlesRepository productoControlesRepository;

    private final ProductoControlesMapper productoControlesMapper;

    public ProductoControlesQueryService(
        ProductoControlesRepository productoControlesRepository,
        ProductoControlesMapper productoControlesMapper
    ) {
        this.productoControlesRepository = productoControlesRepository;
        this.productoControlesMapper = productoControlesMapper;
    }

    /**
     * Return a {@link List} of {@link ProductoControlesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductoControlesDTO> findByCriteria(ProductoControlesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductoControles> specification = createSpecification(criteria);
        return productoControlesMapper.toDto(productoControlesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProductoControlesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductoControlesDTO> findByCriteria(ProductoControlesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductoControles> specification = createSpecification(criteria);
        return productoControlesRepository.findAll(specification, page).map(productoControlesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductoControlesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductoControles> specification = createSpecification(criteria);
        return productoControlesRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductoControlesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProductoControles> createSpecification(ProductoControlesCriteria criteria) {
        Specification<ProductoControles> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProductoControles_.id));
            }
            if (criteria.getDescripcion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescripcion(), ProductoControles_.descripcion));
            }
            if (criteria.getCodigoArancelarioId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCodigoArancelarioId(),
                            root -> root.join(ProductoControles_.codigoArancelario, JoinType.LEFT).get(Producto_.id)
                        )
                    );
            }
            if (criteria.getIdControlId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getIdControlId(),
                            root -> root.join(ProductoControles_.idControl, JoinType.LEFT).get(Control_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
