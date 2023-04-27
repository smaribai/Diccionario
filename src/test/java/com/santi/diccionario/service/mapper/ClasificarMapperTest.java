package com.santi.diccionario.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClasificarMapperTest {

    private ClasificarMapper clasificarMapper;

    @BeforeEach
    public void setUp() {
        clasificarMapper = new ClasificarMapperImpl();
    }
}
