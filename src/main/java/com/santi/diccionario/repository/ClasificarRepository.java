package com.santi.diccionario.repository;

import com.santi.diccionario.domain.Clasificar;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Clasificar entity.
 */
@Repository
public interface ClasificarRepository extends JpaRepository<Clasificar, Long>, JpaSpecificationExecutor<Clasificar> {
    default Optional<Clasificar> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Clasificar> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Clasificar> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct clasificar from Clasificar clasificar left join fetch clasificar.paisOrigen left join fetch clasificar.paisDestino left join fetch clasificar.divisa left join fetch clasificar.idioma left join fetch clasificar.refCliente left join fetch clasificar.provinciaDestino left join fetch clasificar.idRemitente left join fetch clasificar.idDestinatario",
        countQuery = "select count(distinct clasificar) from Clasificar clasificar"
    )
    Page<Clasificar> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct clasificar from Clasificar clasificar left join fetch clasificar.paisOrigen left join fetch clasificar.paisDestino left join fetch clasificar.divisa left join fetch clasificar.idioma left join fetch clasificar.refCliente left join fetch clasificar.provinciaDestino left join fetch clasificar.idRemitente left join fetch clasificar.idDestinatario"
    )
    List<Clasificar> findAllWithToOneRelationships();

    @Query(
        "select clasificar from Clasificar clasificar left join fetch clasificar.paisOrigen left join fetch clasificar.paisDestino left join fetch clasificar.divisa left join fetch clasificar.idioma left join fetch clasificar.refCliente left join fetch clasificar.provinciaDestino left join fetch clasificar.idRemitente left join fetch clasificar.idDestinatario where clasificar.id =:id"
    )
    Optional<Clasificar> findOneWithToOneRelationships(@Param("id") Long id);
}
