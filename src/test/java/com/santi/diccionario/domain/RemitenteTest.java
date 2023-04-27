package com.santi.diccionario.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.santi.diccionario.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RemitenteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Remitente.class);
        Remitente remitente1 = new Remitente();
        remitente1.setId(1L);
        Remitente remitente2 = new Remitente();
        remitente2.setId(remitente1.getId());
        assertThat(remitente1).isEqualTo(remitente2);
        remitente2.setId(2L);
        assertThat(remitente1).isNotEqualTo(remitente2);
        remitente1.setId(null);
        assertThat(remitente1).isNotEqualTo(remitente2);
    }
}
