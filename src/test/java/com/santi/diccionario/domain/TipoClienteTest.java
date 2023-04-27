package com.santi.diccionario.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.santi.diccionario.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TipoClienteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoCliente.class);
        TipoCliente tipoCliente1 = new TipoCliente();
        tipoCliente1.setId(1L);
        TipoCliente tipoCliente2 = new TipoCliente();
        tipoCliente2.setId(tipoCliente1.getId());
        assertThat(tipoCliente1).isEqualTo(tipoCliente2);
        tipoCliente2.setId(2L);
        assertThat(tipoCliente1).isNotEqualTo(tipoCliente2);
        tipoCliente1.setId(null);
        assertThat(tipoCliente1).isNotEqualTo(tipoCliente2);
    }
}
