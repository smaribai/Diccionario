package com.santi.diccionario.repository;

import com.santi.diccionario.domain.Remitente;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Remitente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RemitenteRepository extends JpaRepository<Remitente, Long>, JpaSpecificationExecutor<Remitente> {}
