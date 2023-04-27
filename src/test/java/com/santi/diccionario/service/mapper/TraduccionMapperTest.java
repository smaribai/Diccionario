package com.santi.diccionario.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TraduccionMapperTest {

    private TraduccionMapper traduccionMapper;

    @BeforeEach
    public void setUp() {
        traduccionMapper = new TraduccionMapperImpl();
    }
}
