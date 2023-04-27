package com.santi.diccionario.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.santi.diccionario.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RemitenteDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RemitenteDTO.class);
        RemitenteDTO remitenteDTO1 = new RemitenteDTO();
        remitenteDTO1.setId(1L);
        RemitenteDTO remitenteDTO2 = new RemitenteDTO();
        assertThat(remitenteDTO1).isNotEqualTo(remitenteDTO2);
        remitenteDTO2.setId(remitenteDTO1.getId());
        assertThat(remitenteDTO1).isEqualTo(remitenteDTO2);
        remitenteDTO2.setId(2L);
        assertThat(remitenteDTO1).isNotEqualTo(remitenteDTO2);
        remitenteDTO1.setId(null);
        assertThat(remitenteDTO1).isNotEqualTo(remitenteDTO2);
    }
}
