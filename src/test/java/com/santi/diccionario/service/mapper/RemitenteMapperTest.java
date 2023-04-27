package com.santi.diccionario.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RemitenteMapperTest {

    private RemitenteMapper remitenteMapper;

    @BeforeEach
    public void setUp() {
        remitenteMapper = new RemitenteMapperImpl();
    }
}
