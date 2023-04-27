package com.santi.diccionario.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PaisMapperTest {

    private PaisMapper paisMapper;

    @BeforeEach
    public void setUp() {
        paisMapper = new PaisMapperImpl();
    }
}
