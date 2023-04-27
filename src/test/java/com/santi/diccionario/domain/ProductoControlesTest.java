package com.santi.diccionario.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.santi.diccionario.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductoControlesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductoControles.class);
        ProductoControles productoControles1 = new ProductoControles();
        productoControles1.setId(1L);
        ProductoControles productoControles2 = new ProductoControles();
        productoControles2.setId(productoControles1.getId());
        assertThat(productoControles1).isEqualTo(productoControles2);
        productoControles2.setId(2L);
        assertThat(productoControles1).isNotEqualTo(productoControles2);
        productoControles1.setId(null);
        assertThat(productoControles1).isNotEqualTo(productoControles2);
    }
}
