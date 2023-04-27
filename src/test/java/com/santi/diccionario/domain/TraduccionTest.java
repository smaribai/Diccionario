package com.santi.diccionario.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.santi.diccionario.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TraduccionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Traduccion.class);
        Traduccion traduccion1 = new Traduccion();
        traduccion1.setId(1L);
        Traduccion traduccion2 = new Traduccion();
        traduccion2.setId(traduccion1.getId());
        assertThat(traduccion1).isEqualTo(traduccion2);
        traduccion2.setId(2L);
        assertThat(traduccion1).isNotEqualTo(traduccion2);
        traduccion1.setId(null);
        assertThat(traduccion1).isNotEqualTo(traduccion2);
    }
}
