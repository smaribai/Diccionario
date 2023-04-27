package com.santi.diccionario.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.santi.diccionario.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClasificarTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Clasificar.class);
        Clasificar clasificar1 = new Clasificar();
        clasificar1.setId(1L);
        Clasificar clasificar2 = new Clasificar();
        clasificar2.setId(clasificar1.getId());
        assertThat(clasificar1).isEqualTo(clasificar2);
        clasificar2.setId(2L);
        assertThat(clasificar1).isNotEqualTo(clasificar2);
        clasificar1.setId(null);
        assertThat(clasificar1).isNotEqualTo(clasificar2);
    }
}
