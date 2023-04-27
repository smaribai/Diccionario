package com.santi.diccionario.repository;

import com.santi.diccionario.domain.Divisa;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Divisa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DivisaRepository extends JpaRepository<Divisa, Long>, JpaSpecificationExecutor<Divisa> {}
