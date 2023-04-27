package com.santi.diccionario.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.santi.diccionario.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ControlTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Control.class);
        Control control1 = new Control();
        control1.setId(1L);
        Control control2 = new Control();
        control2.setId(control1.getId());
        assertThat(control1).isEqualTo(control2);
        control2.setId(2L);
        assertThat(control1).isNotEqualTo(control2);
        control1.setId(null);
        assertThat(control1).isNotEqualTo(control2);
    }
}
