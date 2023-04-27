package com.santi.diccionario.repository;

import com.santi.diccionario.domain.TipoCliente;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TipoCliente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoClienteRepository extends JpaRepository<TipoCliente, Long>, JpaSpecificationExecutor<TipoCliente> {}
