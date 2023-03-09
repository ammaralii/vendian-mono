package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ConfigurationsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Configurations.class);
        Configurations configurations1 = new Configurations();
        configurations1.setId(1L);
        Configurations configurations2 = new Configurations();
        configurations2.setId(configurations1.getId());
        assertThat(configurations1).isEqualTo(configurations2);
        configurations2.setId(2L);
        assertThat(configurations1).isNotEqualTo(configurations2);
        configurations1.setId(null);
        assertThat(configurations1).isNotEqualTo(configurations2);
    }
}
