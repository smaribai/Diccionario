package com.santi.diccionario.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.santi.diccionario.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DivisaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DivisaDTO.class);
        DivisaDTO divisaDTO1 = new DivisaDTO();
        divisaDTO1.setId(1L);
        DivisaDTO divisaDTO2 = new DivisaDTO();
        assertThat(divisaDTO1).isNotEqualTo(divisaDTO2);
        divisaDTO2.setId(divisaDTO1.getId());
        assertThat(divisaDTO1).isEqualTo(divisaDTO2);
        divisaDTO2.setId(2L);
        assertThat(divisaDTO1).isNotEqualTo(divisaDTO2);
        divisaDTO1.setId(null);
        assertThat(divisaDTO1).isNotEqualTo(divisaDTO2);
    }
}
