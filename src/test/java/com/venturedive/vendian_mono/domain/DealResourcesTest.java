package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DealResourcesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DealResources.class);
        DealResources dealResources1 = new DealResources();
        dealResources1.setId(1L);
        DealResources dealResources2 = new DealResources();
        dealResources2.setId(dealResources1.getId());
        assertThat(dealResources1).isEqualTo(dealResources2);
        dealResources2.setId(2L);
        assertThat(dealResources1).isNotEqualTo(dealResources2);
        dealResources1.setId(null);
        assertThat(dealResources1).isNotEqualTo(dealResources2);
    }
}
