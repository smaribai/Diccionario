package com.santi.diccionario.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.santi.diccionario.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DestinatarioTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Destinatario.class);
        Destinatario destinatario1 = new Destinatario();
        destinatario1.setId(1L);
        Destinatario destinatario2 = new Destinatario();
        destinatario2.setId(destinatario1.getId());
        assertThat(destinatario1).isEqualTo(destinatario2);
        destinatario2.setId(2L);
        assertThat(destinatario1).isNotEqualTo(destinatario2);
        destinatario1.setId(null);
        assertThat(destinatario1).isNotEqualTo(destinatario2);
    }
}
