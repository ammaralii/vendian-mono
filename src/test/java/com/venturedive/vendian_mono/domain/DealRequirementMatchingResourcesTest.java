package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DealRequirementMatchingResourcesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DealRequirementMatchingResources.class);
        DealRequirementMatchingResources dealRequirementMatchingResources1 = new DealRequirementMatchingResources();
        dealRequirementMatchingResources1.setId(1L);
        DealRequirementMatchingResources dealRequirementMatchingResources2 = new DealRequirementMatchingResources();
        dealRequirementMatchingResources2.setId(dealRequirementMatchingResources1.getId());
        assertThat(dealRequirementMatchingResources1).isEqualTo(dealRequirementMatchingResources2);
        dealRequirementMatchingResources2.setId(2L);
        assertThat(dealRequirementMatchingResources1).isNotEqualTo(dealRequirementMatchingResources2);
        dealRequirementMatchingResources1.setId(null);
        assertThat(dealRequirementMatchingResources1).isNotEqualTo(dealRequirementMatchingResources2);
    }
}
