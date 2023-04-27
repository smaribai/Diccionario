package com.santi.diccionario.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClasificacionesMapperTest {

    private ClasificacionesMapper clasificacionesMapper;

    @BeforeEach
    public void setUp() {
        clasificacionesMapper = new ClasificacionesMapperImpl();
    }
}
