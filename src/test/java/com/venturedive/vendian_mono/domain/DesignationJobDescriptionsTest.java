package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DesignationJobDescriptionsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DesignationJobDescriptions.class);
        DesignationJobDescriptions designationJobDescriptions1 = new DesignationJobDescriptions();
        designationJobDescriptions1.setId(1L);
        DesignationJobDescriptions designationJobDescriptions2 = new DesignationJobDescriptions();
        designationJobDescriptions2.setId(designationJobDescriptions1.getId());
        assertThat(designationJobDescriptions1).isEqualTo(designationJobDescriptions2);
        designationJobDescriptions2.setId(2L);
        assertThat(designationJobDescriptions1).isNotEqualTo(designationJobDescriptions2);
        designationJobDescriptions1.setId(null);
        assertThat(designationJobDescriptions1).isNotEqualTo(designationJobDescriptions2);
    }
}
