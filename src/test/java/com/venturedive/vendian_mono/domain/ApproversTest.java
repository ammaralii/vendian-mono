package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ApproversTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Approvers.class);
        Approvers approvers1 = new Approvers();
        approvers1.setId(1L);
        Approvers approvers2 = new Approvers();
        approvers2.setId(approvers1.getId());
        assertThat(approvers1).isEqualTo(approvers2);
        approvers2.setId(2L);
        assertThat(approvers1).isNotEqualTo(approvers2);
        approvers1.setId(null);
        assertThat(approvers1).isNotEqualTo(approvers2);
    }
}
