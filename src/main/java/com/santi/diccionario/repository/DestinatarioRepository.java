package com.santi.diccionario.repository;

import com.santi.diccionario.domain.Destinatario;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Destinatario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DestinatarioRepository extends JpaRepository<Destinatario, Long>, JpaSpecificationExecutor<Destinatario> {}
