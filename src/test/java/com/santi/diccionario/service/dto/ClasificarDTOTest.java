package com.santi.diccionario.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.santi.diccionario.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClasificarDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClasificarDTO.class);
        ClasificarDTO clasificarDTO1 = new ClasificarDTO();
        clasificarDTO1.setId(1L);
        ClasificarDTO clasificarDTO2 = new ClasificarDTO();
        assertThat(clasificarDTO1).isNotEqualTo(clasificarDTO2);
        clasificarDTO2.setId(clasificarDTO1.getId());
        assertThat(clasificarDTO1).isEqualTo(clasificarDTO2);
        clasificarDTO2.setId(2L);
        assertThat(clasificarDTO1).isNotEqualTo(clasificarDTO2);
        clasificarDTO1.setId(null);
        assertThat(clasificarDTO1).isNotEqualTo(clasificarDTO2);
    }
}
