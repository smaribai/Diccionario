package com.santi.diccionario.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DestinatarioMapperTest {

    private DestinatarioMapper destinatarioMapper;

    @BeforeEach
    public void setUp() {
        destinatarioMapper = new DestinatarioMapperImpl();
    }
}
