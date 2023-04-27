package com.santi.diccionario.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.santi.diccionario.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClasificacionesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClasificacionesDTO.class);
        ClasificacionesDTO clasificacionesDTO1 = new ClasificacionesDTO();
        clasificacionesDTO1.setId(1L);
        ClasificacionesDTO clasificacionesDTO2 = new ClasificacionesDTO();
        assertThat(clasificacionesDTO1).isNotEqualTo(clasificacionesDTO2);
        clasificacionesDTO2.setId(clasificacionesDTO1.getId());
        assertThat(clasificacionesDTO1).isEqualTo(clasificacionesDTO2);
        clasificacionesDTO2.setId(2L);
        assertThat(clasificacionesDTO1).isNotEqualTo(clasificacionesDTO2);
        clasificacionesDTO1.setId(null);
        assertThat(clasificacionesDTO1).isNotEqualTo(clasificacionesDTO2);
    }
}
