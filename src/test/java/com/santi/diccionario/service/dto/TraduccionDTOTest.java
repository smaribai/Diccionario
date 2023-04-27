package com.santi.diccionario.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.santi.diccionario.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TraduccionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TraduccionDTO.class);
        TraduccionDTO traduccionDTO1 = new TraduccionDTO();
        traduccionDTO1.setId(1L);
        TraduccionDTO traduccionDTO2 = new TraduccionDTO();
        assertThat(traduccionDTO1).isNotEqualTo(traduccionDTO2);
        traduccionDTO2.setId(traduccionDTO1.getId());
        assertThat(traduccionDTO1).isEqualTo(traduccionDTO2);
        traduccionDTO2.setId(2L);
        assertThat(traduccionDTO1).isNotEqualTo(traduccionDTO2);
        traduccionDTO1.setId(null);
        assertThat(traduccionDTO1).isNotEqualTo(traduccionDTO2);
    }
}
