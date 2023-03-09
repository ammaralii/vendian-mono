package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProjectLeadershipTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectLeadership.class);
        ProjectLeadership projectLeadership1 = new ProjectLeadership();
        projectLeadership1.setId(1L);
        ProjectLeadership projectLeadership2 = new ProjectLeadership();
        projectLeadership2.setId(projectLeadership1.getId());
        assertThat(projectLeadership1).isEqualTo(projectLeadership2);
        projectLeadership2.setId(2L);
        assertThat(projectLeadership1).isNotEqualTo(projectLeadership2);
        projectLeadership1.setId(null);
        assertThat(projectLeadership1).isNotEqualTo(projectLeadership2);
    }
}
