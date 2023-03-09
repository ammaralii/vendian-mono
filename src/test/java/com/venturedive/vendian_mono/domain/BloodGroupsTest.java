package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BloodGroupsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BloodGroups.class);
        BloodGroups bloodGroups1 = new BloodGroups();
        bloodGroups1.setId(1L);
        BloodGroups bloodGroups2 = new BloodGroups();
        bloodGroups2.setId(bloodGroups1.getId());
        assertThat(bloodGroups1).isEqualTo(bloodGroups2);
        bloodGroups2.setId(2L);
        assertThat(bloodGroups1).isNotEqualTo(bloodGroups2);
        bloodGroups1.setId(null);
        assertThat(bloodGroups1).isNotEqualTo(bloodGroups2);
    }
}
