package com.santi.diccionario.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TipoClienteMapperTest {

    private TipoClienteMapper tipoClienteMapper;

    @BeforeEach
    public void setUp() {
        tipoClienteMapper = new TipoClienteMapperImpl();
    }
}
