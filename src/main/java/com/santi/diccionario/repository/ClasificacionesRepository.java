package com.santi.diccionario.repository;

import com.santi.diccionario.domain.Clasificaciones;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Clasificaciones entity.
 */
@Repository
public interface ClasificacionesRepository extends JpaRepository<Clasificaciones, Long>, JpaSpecificationExecutor<Clasificaciones> {
    default Optional<Clasificaciones> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Clasificaciones> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Clasificaciones> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct clasificaciones from Clasificaciones clasificaciones left join fetch clasificaciones.paisOrigen left join fetch clasificaciones.paisDestino left join fetch clasificaciones.divisa left join fetch clasificaciones.idioma left join fetch clasificaciones.refCliente left join fetch clasificaciones.provinciaDestino left join fetch clasificaciones.idRemitente left join fetch clasificaciones.idDestinatario",
        countQuery = "select count(distinct clasificaciones) from Clasificaciones clasificaciones"
    )
    Page<Clasificaciones> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct clasificaciones from Clasificaciones clasificaciones left join fetch clasificaciones.paisOrigen left join fetch clasificaciones.paisDestino left join fetch clasificaciones.divisa left join fetch clasificaciones.idioma left join fetch clasificaciones.refCliente left join fetch clasificaciones.provinciaDestino left join fetch clasificaciones.idRemitente left join fetch clasificaciones.idDestinatario"
    )
    List<Clasificaciones> findAllWithToOneRelationships();

    @Query(
        "select clasificaciones from Clasificaciones clasificaciones left join fetch clasificaciones.paisOrigen left join fetch clasificaciones.paisDestino left join fetch clasificaciones.divisa left join fetch clasificaciones.idioma left join fetch clasificaciones.refCliente left join fetch clasificaciones.provinciaDestino left join fetch clasificaciones.idRemitente left join fetch clasificaciones.idDestinatario where clasificaciones.id =:id"
    )
    Optional<Clasificaciones> findOneWithToOneRelationships(@Param("id") Long id);
}
