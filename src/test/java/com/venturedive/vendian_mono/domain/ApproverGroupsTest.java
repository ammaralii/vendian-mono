package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ApproverGroupsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApproverGroups.class);
        ApproverGroups approverGroups1 = new ApproverGroups();
        approverGroups1.setId(1L);
        ApproverGroups approverGroups2 = new ApproverGroups();
        approverGroups2.setId(approverGroups1.getId());
        assertThat(approverGroups1).isEqualTo(approverGroups2);
        approverGroups2.setId(2L);
        assertThat(approverGroups1).isNotEqualTo(approverGroups2);
        approverGroups1.setId(null);
        assertThat(approverGroups1).isNotEqualTo(approverGroups2);
    }
}
