package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ApproverFlowsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApproverFlows.class);
        ApproverFlows approverFlows1 = new ApproverFlows();
        approverFlows1.setId(1L);
        ApproverFlows approverFlows2 = new ApproverFlows();
        approverFlows2.setId(approverFlows1.getId());
        assertThat(approverFlows1).isEqualTo(approverFlows2);
        approverFlows2.setId(2L);
        assertThat(approverFlows1).isNotEqualTo(approverFlows2);
        approverFlows1.setId(null);
        assertThat(approverFlows1).isNotEqualTo(approverFlows2);
    }
}
