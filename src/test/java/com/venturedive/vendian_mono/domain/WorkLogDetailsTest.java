package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WorkLogDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkLogDetails.class);
        WorkLogDetails workLogDetails1 = new WorkLogDetails();
        workLogDetails1.setId(1L);
        WorkLogDetails workLogDetails2 = new WorkLogDetails();
        workLogDetails2.setId(workLogDetails1.getId());
        assertThat(workLogDetails1).isEqualTo(workLogDetails2);
        workLogDetails2.setId(2L);
        assertThat(workLogDetails1).isNotEqualTo(workLogDetails2);
        workLogDetails1.setId(null);
        assertThat(workLogDetails1).isNotEqualTo(workLogDetails2);
    }
}
