package com.santi.diccionario.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductoControlesMapperTest {

    private ProductoControlesMapper productoControlesMapper;

    @BeforeEach
    public void setUp() {
        productoControlesMapper = new ProductoControlesMapperImpl();
    }
}
