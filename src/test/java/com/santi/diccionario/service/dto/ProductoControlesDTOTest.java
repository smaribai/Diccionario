package com.santi.diccionario.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.santi.diccionario.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductoControlesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductoControlesDTO.class);
        ProductoControlesDTO productoControlesDTO1 = new ProductoControlesDTO();
        productoControlesDTO1.setId(1L);
        ProductoControlesDTO productoControlesDTO2 = new ProductoControlesDTO();
        assertThat(productoControlesDTO1).isNotEqualTo(productoControlesDTO2);
        productoControlesDTO2.setId(productoControlesDTO1.getId());
        assertThat(productoControlesDTO1).isEqualTo(productoControlesDTO2);
        productoControlesDTO2.setId(2L);
        assertThat(productoControlesDTO1).isNotEqualTo(productoControlesDTO2);
        productoControlesDTO1.setId(null);
        assertThat(productoControlesDTO1).isNotEqualTo(productoControlesDTO2);
    }
}
