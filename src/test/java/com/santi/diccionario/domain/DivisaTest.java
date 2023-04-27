package com.santi.diccionario.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.santi.diccionario.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DivisaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Divisa.class);
        Divisa divisa1 = new Divisa();
        divisa1.setId(1L);
        Divisa divisa2 = new Divisa();
        divisa2.setId(divisa1.getId());
        assertThat(divisa1).isEqualTo(divisa2);
        divisa2.setId(2L);
        assertThat(divisa1).isNotEqualTo(divisa2);
        divisa1.setId(null);
        assertThat(divisa1).isNotEqualTo(divisa2);
    }
}
