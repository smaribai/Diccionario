package com.santi.diccionario.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.santi.diccionario.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ControlDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ControlDTO.class);
        ControlDTO controlDTO1 = new ControlDTO();
        controlDTO1.setId(1L);
        ControlDTO controlDTO2 = new ControlDTO();
        assertThat(controlDTO1).isNotEqualTo(controlDTO2);
        controlDTO2.setId(controlDTO1.getId());
        assertThat(controlDTO1).isEqualTo(controlDTO2);
        controlDTO2.setId(2L);
        assertThat(controlDTO1).isNotEqualTo(controlDTO2);
        controlDTO1.setId(null);
        assertThat(controlDTO1).isNotEqualTo(controlDTO2);
    }
}
