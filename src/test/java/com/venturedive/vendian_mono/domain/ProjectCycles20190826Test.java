package com.venturedive.vendian_mono.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.venturedive.vendian_mono.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProjectCycles20190826Test {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectCycles20190826.class);
        ProjectCycles20190826 projectCycles201908261 = new ProjectCycles20190826();
        projectCycles201908261.setId(1L);
        ProjectCycles20190826 projectCycles201908262 = new ProjectCycles20190826();
        projectCycles201908262.setId(projectCycles201908261.getId());
        assertThat(projectCycles201908261).isEqualTo(projectCycles201908262);
        projectCycles201908262.setId(2L);
        assertThat(projectCycles201908261).isNotEqualTo(projectCycles201908262);
        projectCycles201908261.setId(null);
        assertThat(projectCycles201908261).isNotEqualTo(projectCycles201908262);
    }
}
