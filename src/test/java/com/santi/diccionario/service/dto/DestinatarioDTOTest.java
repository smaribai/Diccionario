package com.santi.diccionario.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.santi.diccionario.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DestinatarioDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DestinatarioDTO.class);
        DestinatarioDTO destinatarioDTO1 = new DestinatarioDTO();
        destinatarioDTO1.setId(1L);
        DestinatarioDTO destinatarioDTO2 = new DestinatarioDTO();
        assertThat(destinatarioDTO1).isNotEqualTo(destinatarioDTO2);
        destinatarioDTO2.setId(destinatarioDTO1.getId());
        assertThat(destinatarioDTO1).isEqualTo(destinatarioDTO2);
        destinatarioDTO2.setId(2L);
        assertThat(destinatarioDTO1).isNotEqualTo(destinatarioDTO2);
        destinatarioDTO1.setId(null);
        assertThat(destinatarioDTO1).isNotEqualTo(destinatarioDTO2);
    }
}
