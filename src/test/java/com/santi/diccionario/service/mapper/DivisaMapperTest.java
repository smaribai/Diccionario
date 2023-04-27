package com.santi.diccionario.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DivisaMapperTest {

    private DivisaMapper divisaMapper;

    @BeforeEach
    public void setUp() {
        divisaMapper = new DivisaMapperImpl();
    }
}
