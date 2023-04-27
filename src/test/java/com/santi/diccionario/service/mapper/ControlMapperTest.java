package com.santi.diccionario.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ControlMapperTest {

    private ControlMapper controlMapper;

    @BeforeEach
    public void setUp() {
        controlMapper = new ControlMapperImpl();
    }
}
