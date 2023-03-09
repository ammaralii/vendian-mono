package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DealRequirementsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DealRequirements.class);
        DealRequirements dealRequirements1 = new DealRequirements();
        dealRequirements1.setId(1L);
        DealRequirements dealRequirements2 = new DealRequirements();
        dealRequirements2.setId(dealRequirements1.getId());
        assertThat(dealRequirements1).isEqualTo(dealRequirements2);
        dealRequirements2.setId(2L);
        assertThat(dealRequirements1).isNotEqualTo(dealRequirements2);
        dealRequirements1.setId(null);
        assertThat(dealRequirements1).isNotEqualTo(dealRequirements2);
    }
}
