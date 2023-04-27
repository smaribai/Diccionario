package com.santi.diccionario.repository;

import com.santi.diccionario.domain.ProductoControles;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductoControles entity.
 */
@Repository
public interface ProductoControlesRepository extends JpaRepository<ProductoControles, Long>, JpaSpecificationExecutor<ProductoControles> {
    default Optional<ProductoControles> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ProductoControles> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ProductoControles> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct productoControles from ProductoControles productoControles left join fetch productoControles.codigoArancelario left join fetch productoControles.idControl",
        countQuery = "select count(distinct productoControles) from ProductoControles productoControles"
    )
    Page<ProductoControles> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct productoControles from ProductoControles productoControles left join fetch productoControles.codigoArancelario left join fetch productoControles.idControl"
    )
    List<ProductoControles> findAllWithToOneRelationships();

    @Query(
        "select productoControles from ProductoControles productoControles left join fetch productoControles.codigoArancelario left join fetch productoControles.idControl where productoControles.id =:id"
    )
    Optional<ProductoControles> findOneWithToOneRelationships(@Param("id") Long id);
}
