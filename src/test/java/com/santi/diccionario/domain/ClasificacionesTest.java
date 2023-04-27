package com.santi.diccionario.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.santi.diccionario.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClasificacionesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Clasificaciones.class);
        Clasificaciones clasificaciones1 = new Clasificaciones();
        clasificaciones1.setId(1L);
        Clasificaciones clasificaciones2 = new Clasificaciones();
        clasificaciones2.setId(clasificaciones1.getId());
        assertThat(clasificaciones1).isEqualTo(clasificaciones2);
        clasificaciones2.setId(2L);
        assertThat(clasificaciones1).isNotEqualTo(clasificaciones2);
        clasificaciones1.setId(null);
        assertThat(clasificaciones1).isNotEqualTo(clasificaciones2);
    }
}
