package com.santi.diccionario.repository;

import com.santi.diccionario.domain.Traduccion;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Traduccion entity.
 */
@Repository
public interface TraduccionRepository extends JpaRepository<Traduccion, Long>, JpaSpecificationExecutor<Traduccion> {
    default Optional<Traduccion> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Traduccion> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Traduccion> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct traduccion from Traduccion traduccion left join fetch traduccion.idiomaOrigen left join fetch traduccion.idiomaDestino",
        countQuery = "select count(distinct traduccion) from Traduccion traduccion"
    )
    Page<Traduccion> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct traduccion from Traduccion traduccion left join fetch traduccion.idiomaOrigen left join fetch traduccion.idiomaDestino"
    )
    List<Traduccion> findAllWithToOneRelationships();

    @Query(
        "select traduccion from Traduccion traduccion left join fetch traduccion.idiomaOrigen left join fetch traduccion.idiomaDestino where traduccion.id =:id"
    )
    Optional<Traduccion> findOneWithToOneRelationships(@Param("id") Long id);
}
