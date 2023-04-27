package com.santi.diccionario.repository;

import com.santi.diccionario.domain.Idioma;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Idioma entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IdiomaRepository extends JpaRepository<Idioma, Long>, JpaSpecificationExecutor<Idioma> {}
